package com.github.didiloy.promohub.api;


import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

public class Store {
    public String storeID;
    public String storeName;
    public String isActive;

    public Map<String, String> images;

    public Store(String storeId, String storeName, String isActive) {
        this.storeID = storeId;
        this.storeName = storeName;
        this.isActive = isActive;
        this.images = new LinkedTreeMap<>();
    }
}
