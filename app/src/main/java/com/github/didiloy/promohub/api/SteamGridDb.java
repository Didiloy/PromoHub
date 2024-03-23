package com.github.didiloy.promohub.api;

import com.github.didiloy.promohub.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SteamGridDb {
    public final static String key = "f22864eb260b777b19942d7da80e169d";
    public final static String BASE_URL = "https://www.steamgriddb.com/api/v2";
    public final static String NAME_SEARCH = "/search/autocomplete/";
    public final static String HEROE_SEARCH = "/heroes/game/";
    public final static String GRID_SEARCH = "/grids/game/";

    public static int getGameIDByName(String name) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + NAME_SEARCH + name)
                .addHeader("Authorization", "Bearer " + key)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                JSONObject firstObject = dataArray.getJSONObject(0);
                return firstObject.getInt("id");
            } else {
                MainActivity.logger.severe("Request failed: " + response.code());
            }
        } catch (IOException e) {
            MainActivity.logger.severe("Failed to fetch game by name: " + e.getMessage());
        } catch (JSONException e) {
            MainActivity.logger.severe("Failed to fetch game by name: " + e.getMessage());
        }
        return 0;
    }

    public static String getGameHero(int Id){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + HEROE_SEARCH + Id)
                .addHeader("Authorization", "Bearer " + key)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                MainActivity.logger.info("Response: " + responseBody);
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                JSONObject firstObject = dataArray.getJSONObject(0);
                return firstObject.getString("url");
            } else {
                MainActivity.logger.severe("Request failed: " + response.code());
            }
        } catch (IOException e) {
            MainActivity.logger.severe("Failed to fetch game hero: " + e.getMessage());
        } catch (JSONException e) {
            MainActivity.logger.severe("Failed to fetch game hero: " + e.getMessage());
        }
        return null;
    }

    public static String getGameGrid(int Id){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + GRID_SEARCH + Id)
                .addHeader("Authorization", "Bearer " + key)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                MainActivity.logger.info("Response: " + responseBody);
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                JSONObject firstObject = dataArray.getJSONObject(0);
                return firstObject.getString("url");
            } else {
                MainActivity.logger.severe("Request failed: " + response.code());
            }
        } catch (IOException e) {
            MainActivity.logger.severe("Failed to fetch game grid: " + e.getMessage());
        } catch (JSONException e) {
            MainActivity.logger.severe("Failed to fetch game grid: " + e.getMessage());
        }
        return null;
    }

}
