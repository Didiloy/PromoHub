package com.github.didiloy.promohub.api;

import java.util.Arrays;

public class CheapShark {
    public static final String BASE_URL = "https://www.cheapshark.com/api/1.0/";
    public static final String DEALS = "deals";
    public static final String STORES = "stores";
    public static final String IMG_BASE_URL = "https://www.cheapshark.com";

    public static Store[] filterActiveStores(Store[] stores) {
        return Arrays.stream(stores)
                .filter(store -> store.isActive.equals("1"))
                .toArray(Store[]::new);
    }

}