package com.github.didiloy.promohub.api;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.didiloy.promohub.MainActivity;
import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.database.AppDatabase;
import com.github.didiloy.promohub.database.OwnedGame;
import com.github.didiloy.promohub.database.OwnedGameDao;
import com.github.didiloy.promohub.select_store.StoreAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CheapShark {
    public static final String BASE_URL = "https://www.cheapshark.com/api/1.0/";
    public static final String DEALS = "deals";
    public static final String STORES = "stores";
    public static final String GAMES_BY_NAME = "games?title=";
    public static final String IMG_BASE_URL = "https://www.cheapshark.com";

    public static final String REDIRECT_BASE_URL = "https://www.cheapshark.com/redirect?dealID=";

    public static Store[] stores;

    public static Store[] getStores() {
        if(stores == null) {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            Future<Store[]> future = executorService.submit(new StoreFetcherCallable());
            try {
                stores = future.get();
                //initialise the isChecked field of the store
                for (Store store : stores) {
                    store.isChecked = true;
                }
                stores = CheapShark.filterActiveStores(stores);
            } catch (InterruptedException | ExecutionException e) {
                MainActivity.logger.severe("Failed to fetch stores: " + e.getMessage());
            } finally {
                executorService.shutdown();
            }
        }
        return stores;
    }

    public static Deal[] getDealsByGameName(String name, boolean exactMatch){
        Deal[] deals = null;
        String url = BASE_URL + GAMES_BY_NAME + name + "&exact=" + (exactMatch ? "1" : "0");
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Deal[]> future = executorService.submit(new DealFetcherCallable(url));
        try {
            deals = future.get();
        } catch (InterruptedException | ExecutionException e) {
            MainActivity.logger.severe("Failed to fetch deals: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }
        return deals;
    }

    public static Deal[] filterOwnedGames(Deal[] deals){
        AppDatabase db = AppDatabase.getInstance();
        OwnedGameDao ownedGameDao = db.ownedGameDao();
        ArrayList<OwnedGame> ownedGameEntities = (ArrayList<OwnedGame>) ownedGameDao.getAll();
        if (ownedGameEntities == null || ownedGameEntities.isEmpty()) {
            return deals;
        }
        return Arrays.stream(deals)
                .filter(deal -> ownedGameEntities.stream()
                        .noneMatch(ownedGame -> ownedGame.name.equals(deal.title)))
                .toArray(Deal[]::new);
    }

    public static Deal[] getDeals() {
        Deal[] deals = null;
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Deal[]> future = executorService.submit(new DealFetcherCallable());
        try {
            deals = future.get();
        } catch (InterruptedException | ExecutionException e) {
            MainActivity.logger.severe("Failed to fetch deals: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }
        return deals;
    }

    public static Deal[] getDeals(Map<String, String> parameters) {
        StringBuilder url = new StringBuilder(BASE_URL + DEALS + "?");
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        MainActivity.logger.info("Fetching deals with parameters: " + url);

        Deal[] deals = null;
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        DealFetcherCallable dealFetcherCallable = new DealFetcherCallable(url.toString());
        Future<Deal[]> future = executorService.submit(dealFetcherCallable);
        try {
            deals = future.get();
        } catch (InterruptedException | ExecutionException e) {
            MainActivity.logger.severe("Failed to fetch deals: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }
        return deals;
    }

    public static Store[] filterActiveStores(Store[] stores) {
        return Arrays.stream(stores)
                .filter(store -> store.isActive.equals("1"))
                .toArray(Store[]::new);
    }

    public static void sortStoresAlphabetically(Store[] stores) {
        Arrays.sort(stores, Comparator.comparing(store -> store.storeName));
    }

    public static void sortStoresById(Store[] stores) {
        Arrays.sort(stores, Comparator.comparing(store -> store.storeID));
    }

    public static String getStoreNameById(String id){
        if(stores == null){
            stores = getStores();
        }
        return Arrays.stream(stores)
                .filter(store -> store.storeID.equals(id))
                .findFirst()
                .map(store -> store.storeName)
                .orElse("Unknown");
    }

}
