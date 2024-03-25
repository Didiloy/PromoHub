package com.github.didiloy.promohub.results;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.github.didiloy.promohub.database.AppDatabase;
import com.github.didiloy.promohub.database.DealDao;
import com.github.didiloy.promohub.database.DealEntity;

import java.util.ArrayList;

public class SavedDealsActivity extends AppCompatActivity {

    Deal[] deals;
    RecyclerView recycler_view_results;
    DealAdapter dealAdapter;
    ProgressBar progressBar;
    TextView textview_activity_title;

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
        textview_activity_title = findViewById(R.id.textview_activity_title);
        textview_activity_title.setText(R.string.saved_deals);
        init();
    }

    public void init(){
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance();
            DealDao dealDao = db.dealDao();
            ArrayList<DealEntity> dealEntities = (ArrayList<DealEntity>) dealDao.getAll();
            if (dealEntities == null || dealEntities.isEmpty()) {
                deals = new Deal[0];
                runOnUiThread(() -> {
                    MainActivity.logger.severe("Failed to fetch deals");
                    textview_activity_title.setText(R.string.no_saved_deals);
                    progressBar.setVisibility(ProgressBar.GONE);
                    dealAdapter = new DealAdapter(this, deals);
                    recycler_view_results.setAdapter(dealAdapter);
                    recycler_view_results.setLayoutManager(new LinearLayoutManager(this));
                });
                return;
            }
            deals = new Deal[dealEntities.size()];
            for (int i = 0; i < dealEntities.size(); i++) {
                deals[i] = dealEntities.get(i).toDeal();
            }
            runOnUiThread(() -> {
                progressBar.setVisibility(ProgressBar.GONE);
                dealAdapter = new DealAdapter(this, deals);
                recycler_view_results.setAdapter(dealAdapter);
                recycler_view_results.setLayoutManager(new LinearLayoutManager(this));
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}