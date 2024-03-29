package com.github.didiloy.promohub.settings;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class DataStoreSingleton {
    private static final Preferences pref_error = new Preferences() {
        @Nullable
        @Override
        public <T> T get(@NonNull Key<T> key) {
            return null;
        }

        @Override
        public <T> boolean contains(@NonNull Key<T> key) {
            return false;
        }

        @NonNull
        @Override
        public Map<Key<?>, Object> asMap() {
            return new HashMap<>();
        }
    };

    RxDataStore<Preferences> datastore;

    private static final DataStoreSingleton instance = new DataStoreSingleton();

    private DataStoreSingleton() { }

    // If the instance hasn't been initialised yet, initialise it.
    public static DataStoreSingleton getInstance(Context context) {
        if (instance.datastore == null) {
            instance.datastore = new RxPreferenceDataStoreBuilder(context, "settings").build();
        }

        return instance;
    }

    public boolean setStringValue(String Key, String value){
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);

        Single<Preferences> updateResult =  datastore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_KEY, value);
            return Single.just(mutablePreferences);
        }).onErrorReturnItem(pref_error);

        return updateResult.blockingGet() != pref_error;
    }

    public String getStringValue(String Key) {
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<String> value = datastore.data().firstOrError().map(prefs -> prefs.get(PREF_KEY)).onErrorReturnItem("null");
        return value.blockingGet();
    }

    public boolean setBoolValue(String Key, boolean boolValue){
        String value = boolValue ? "1" : "0";
        return setStringValue(Key, value);
    }

    public boolean getBoolValue(String Key) {
        return getStringValue(Key).equals("1");
    }

    public boolean setIntValue(String Key, int value){
        Preferences.Key<Integer> PREF_KEY = PreferencesKeys.intKey(Key);

        Single<Preferences> updateResult =  datastore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_KEY, value);
            return Single.just(mutablePreferences);
        }).onErrorReturnItem(pref_error);

        return updateResult.blockingGet() != pref_error;
    }

    public Integer getIntValue(String Key) {
        Preferences.Key<Integer> PREF_KEY = PreferencesKeys.intKey(Key);
        Single<Integer> value = datastore.data().firstOrError().map(prefs -> prefs.get(PREF_KEY)).onErrorReturnItem(-1);
        return value.blockingGet();
    }
}
