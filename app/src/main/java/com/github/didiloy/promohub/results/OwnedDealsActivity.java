package com.github.didiloy.promohub.results;

import android.os.Bundle;
import android.widget.ProgressBar;
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
import com.github.didiloy.promohub.api.Deal;
import com.github.didiloy.promohub.database.AppDatabase;
import com.github.didiloy.promohub.database.DealDao;
import com.github.didiloy.promohub.database.DealEntity;
import com.github.didiloy.promohub.database.OwnedGame;
import com.github.didiloy.promohub.database.OwnedGameDao;

import java.util.ArrayList;

public class OwnedDealsActivity extends AppCompatActivity {

    OwnedGame[] ownedGames;
    RecyclerView recycler_view_results;
    OwnedGameAdapter owned_game_adapter;
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
        textview_activity_title.setText(R.string.owned_games);

        init();
    }

    public void init(){
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance();
            OwnedGameDao ownedGameDao = db.ownedGameDao();
            ArrayList<OwnedGame> ownedGameEntities = (ArrayList<OwnedGame>) ownedGameDao.getAll();
            if (ownedGameEntities == null || ownedGameEntities.isEmpty()) {
                runOnUiThread(() -> {
                    MainActivity.logger.severe("Failed to fetch owned games.");
                    textview_activity_title.setText(R.string.no_saved_games);
                    progressBar.setVisibility(ProgressBar.GONE);
                    owned_game_adapter = new OwnedGameAdapter(this, ownedGameEntities);
                    recycler_view_results.setAdapter(owned_game_adapter);
                    recycler_view_results.setLayoutManager(new LinearLayoutManager(this));
                });
                return;
            }

            runOnUiThread(() -> {
                progressBar.setVisibility(ProgressBar.GONE);
                owned_game_adapter = new OwnedGameAdapter(this, ownedGameEntities);
                recycler_view_results.setAdapter(owned_game_adapter);
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