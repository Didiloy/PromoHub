package com.github.didiloy.promohub.selectstore;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

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
import com.github.didiloy.promohub.api.Store;
import com.github.didiloy.promohub.api.StoreFetcherCallable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SelectStore extends AppCompatActivity {

    RecyclerView recyclerView;
    Store[] stores;

    TextView textView_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_store);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recycler_view_store);
        textView_error = findViewById(R.id.textView_error);

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Store[]> future = executorService.submit(new StoreFetcherCallable());
        try {
            stores = future.get();
            stores = CheapShark.filterActiveStores(stores);
            StoreAdapter storeAdapter = new StoreAdapter(this, stores);
            recyclerView.setAdapter(storeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } catch (InterruptedException | ExecutionException e) {
            MainActivity.logger.severe("Failed to fetch stores: " + e.getMessage());
            textView_error.setText(R.string.error_loading_store);
        } finally {
            executorService.shutdown();
        }
    }
}