package com.github.didiloy.promohub.results;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.didiloy.promohub.MainActivity;
import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.api.CheapShark;
import com.github.didiloy.promohub.api.Deal;
import com.github.didiloy.promohub.api.DealFetcherCallable;
import com.github.didiloy.promohub.api.Store;
import com.github.didiloy.promohub.api.StoreFetcherCallable;
import com.github.didiloy.promohub.select_store.StoreAdapter;
import com.github.didiloy.promohub.settings.DataStoreSingleton;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ResultsActivity extends AppCompatActivity {

    private String selectedStores;
    private int numberOfDeals;
    private int maxAgeOfDeals;
    private String sortBy;
    private String sortOrder;

    private int minPrice;
    private int maxPrice;

    private int aaaFilter;
    private int onSaleFilter;
    private int metacriticRating;
    private int steamRating;

    private RecyclerView recycler_view_results;

    Deal[] deals;

    HashMap<String, String> parameters = new HashMap<>();

    DealAdapter dealAdapter;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        retrieveDataFromIntent();
        logRetrievedValues();

        recycler_view_results = findViewById(R.id.recycler_view_results);
        progressBar = findViewById(R.id.progressBar);

        new Thread(() -> {
            deals = CheapShark.getDeals(parameters);
            if (deals == null) {
                MainActivity.logger.severe("Failed to fetch deals");
                return;
            }
            if(DataStoreSingleton.getInstance(this).getBoolValue("HIDE_OWNED_GAMES")){
                deals = CheapShark.filterOwnedGames(deals);
            }
            runOnUiThread(() -> {
                progressBar.setVisibility(ProgressBar.GONE);
                dealAdapter = new DealAdapter(this, deals);
                recycler_view_results.setAdapter(dealAdapter);
                recycler_view_results.setLayoutManager(new LinearLayoutManager(this));
            });
        }).start();
    }

    private void retrieveDataFromIntent() {
        Intent intent = getIntent();

        if (intent.getStringExtra("selectedStores") != null)
            selectedStores = intent.getStringExtra("selectedStores");
        else selectedStores = "";

        numberOfDeals = intent.getIntExtra("numberOfDeals", 0);
        maxAgeOfDeals = intent.getIntExtra("maxAgeOfDeals", 0);
        if (intent.getStringExtra("sortBy") != null)
            sortBy = intent.getStringExtra("sortBy");
        else sortBy = "";
        if (intent.getStringExtra("sortOrder") != null)
            sortOrder = intent.getStringExtra("sortOrder");
        else sortOrder = "";

        minPrice = intent.getIntExtra("minPrice", 0);
        maxPrice = intent.getIntExtra("maxPrice", 50); //50 act the same as no limit

        aaaFilter = intent.getIntExtra("AAA", 0);
        onSaleFilter = intent.getIntExtra("onSale", 0);

        metacriticRating = intent.getIntExtra("metacritic", 0);
        steamRating = intent.getIntExtra("steam", 0);

        parameters.put("storeID", selectedStores);
        parameters.put("pageSize", String.valueOf(numberOfDeals));
        parameters.put("maxAge", String.valueOf(maxAgeOfDeals));
        parameters.put("sortBy", sortBy);
        parameters.put("desc", sortOrder);
        parameters.put("lowerPrice", String.valueOf(minPrice));
        parameters.put("upperPrice", String.valueOf(maxPrice));
        parameters.put("AAA", String.valueOf(aaaFilter));
        parameters.put("onSale", String.valueOf(onSaleFilter));
        parameters.put("metacritic", String.valueOf(metacriticRating));
        parameters.put("steam", String.valueOf(steamRating));
    }

    private void logRetrievedValues() {
        String message = "Results Activity - Retrieved Values:\n";
        message += "Selected Stores: " + selectedStores + "\n";
        message += "Number of Deals: " + numberOfDeals + "\n";
        message += "Max Age of Deals: " + maxAgeOfDeals + "\n";
        message += "Sort By: " + sortBy + "\n";
        message += "Sort Order: " + sortOrder + "\n";
        message += "Min Price: " + minPrice + "\n";
        message += "Max Price: " + maxPrice + "\n";
        message += "AAA Filter: " + aaaFilter + "\n";
        message += "On Sale Filter: " + onSaleFilter + "\n";
        message += "Metacritic Rating: " + metacriticRating + "\n";
        message += "Steam Rating: " + steamRating + "\n";

        MainActivity.logger.info("===========\nResultsActivity: " + message + "===========");
    }
}