package com.github.didiloy.promohub.api;

import static com.github.didiloy.promohub.api.CheapShark.BASE_URL;
import static com.github.didiloy.promohub.api.CheapShark.DEALS;

import com.github.didiloy.promohub.MainActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DealFetcherCallable implements Callable<Deal[]> {

    final String DEFAULT_URL = BASE_URL + DEALS;
    String possible_url;

    public DealFetcherCallable() {
        this.possible_url = "";
    }
    public DealFetcherCallable(String url) {
        this.possible_url = url;
    }
    @Override
    public Deal[] call() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Objects.equals(possible_url, "") ? DEFAULT_URL : possible_url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                return parseDealsFromJson(jsonData);
            } else {
                throw new IOException("Failed to fetch stores: " + response.code());
            }
        }
    }

    private static Deal[] parseDealsFromJson(String jsonData) {
        MainActivity.logger.info("Parsing deals from JSON: " + jsonData);
        Gson gson = new Gson();
        return gson.fromJson(jsonData, Deal[].class);
    }
}
