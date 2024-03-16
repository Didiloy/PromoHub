package com.github.didiloy.promohub.api;

import static com.github.didiloy.promohub.api.CheapShark.BASE_URL;
import static com.github.didiloy.promohub.api.CheapShark.STORES;

import com.github.didiloy.promohub.MainActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StoreFetcherCallable implements Callable<Store[]> {

    @Override
    public Store[] call() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + STORES)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                return parseStoresFromJson(jsonData);
            } else {
                throw new IOException("Failed to fetch stores: " + response.code());
            }
        }
    }

    // This method parses the JSON data and creates Store objects (replace with your actual logic)
    private static Store[] parseStoresFromJson(String jsonData) {
        MainActivity.logger.info("Parsing stores from JSON: " + jsonData);
        Gson gson = new Gson();
        return gson.fromJson(jsonData, Store[].class);
    }
}