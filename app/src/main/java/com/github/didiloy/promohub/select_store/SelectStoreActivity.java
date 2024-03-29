package com.github.didiloy.promohub.select_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.api.CheapShark;
import com.github.didiloy.promohub.api.Store;
import com.github.didiloy.promohub.select_deals_parameters.SelectDealsParametersActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SelectStoreActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Store[] stores;

    TextView textView_error;

    SwitchMaterial switch_alphabetical_order;

    Button nextButton;

    ProgressBar progressBar;

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
        switch_alphabetical_order = findViewById(R.id.switchMaterial);
        nextButton = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar2);


        new Thread(() -> {
            stores = CheapShark.getStores();
            if (stores == null) {
                runOnUiThread(() -> textView_error.setText(R.string.error_loading_store));
                return;
            }
            runOnUiThread(() -> {
                progressBar.setVisibility(ProgressBar.GONE);
                StoreAdapter storeAdapter = new StoreAdapter(this, stores);
                recyclerView.setAdapter(storeAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            });
        }).start();
    }

    public void onSwitchAlphabeticalOrderClicked(View view) {
        if (switch_alphabetical_order.isChecked()) {
            CheapShark.sortStoresAlphabetically(stores);
        } else {
            CheapShark.sortStoresById(stores);
        }
        if (recyclerView.getAdapter() != null)
            recyclerView.getAdapter().notifyItemRangeChanged(0, stores.length);
    }

    public String getListOfSelectedStores() {
        StringBuilder selectedStores = new StringBuilder();
        for (Store store : stores) {
            if (store.isChecked) {
                selectedStores.append(store.storeID).append(",");
            }
        }
        if (selectedStores.length() > 0) {
            selectedStores.deleteCharAt(selectedStores.length() - 1);
        }
        return selectedStores.toString();
    }

    public void onNextButtonClick(View v) {
        Intent intent = new Intent(this, SelectDealsParametersActivity.class);
        intent.putExtra("selectedStores", getListOfSelectedStores());
        startActivity(intent);
    }
}