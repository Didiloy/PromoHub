package com.github.didiloy.promohub.api;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

public class Store {
    public String storeID;
    public String storeName;
    public String isActive;

    public Map<String, String> images;

    /**
     * This field is used to keep track of the checkbox state in the recycler view
     */
    public Boolean isChecked;
}
