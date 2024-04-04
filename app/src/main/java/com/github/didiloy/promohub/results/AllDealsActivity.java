package com.github.didiloy.promohub.results;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class AllDealsActivity extends AppCompatActivity {

    Deal[] deals;
    RecyclerView recycler_view_results;
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
        recycler_view_results = findViewById(R.id.recycler_view_results);
        progressBar = findViewById(R.id.progressBar);

        new Thread(() -> {
            deals = CheapShark.getDeals();
            if (deals == null) {
                MainActivity.logger.severe("Failed to fetch deals");
                Toast toast = Toast.makeText(this, "Failed to fetch deals", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            runOnUiThread(() -> {
                progressBar.setVisibility(ProgressBar.GONE);
                dealAdapter = new DealAdapter(this, deals);
                recycler_view_results.setAdapter(dealAdapter);
                recycler_view_results.setLayoutManager(new LinearLayoutManager(this));
            });
        }).start();
    }
}