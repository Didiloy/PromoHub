package com.github.didiloy.promohub.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.didiloy.promohub.MainActivity;
import com.github.didiloy.promohub.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    RecyclerView recyclerview_credit;

    ConstraintLayout constraintLayout_source_code;

    SwitchMaterial switch_save_deal;
    SwitchMaterial switch_hide_owned_games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerview_credit = findViewById(R.id.recyclerview_credit);
        CreditAdapter creditAdapter = new CreditAdapter(this, getCredits());
        recyclerview_credit.setAdapter(creditAdapter);
        recyclerview_credit.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        constraintLayout_source_code = findViewById(R.id.constraintLayout_source_code);
        constraintLayout_source_code.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_value)));
            startActivity(browserIntent);
        });

        switch_save_deal = findViewById(R.id.switch_save_deal);
        switch_save_deal.setChecked(DataStoreSingleton.getInstance(this).getBoolValue("SAVE_SETTINGS"));
        switch_save_deal.setOnCheckedChangeListener((view, state)->{
            DataStoreSingleton.getInstance(this).setBoolValue("SAVE_SETTINGS", state);
        });

        switch_hide_owned_games = findViewById(R.id.switch_hide_owned_games);
        switch_hide_owned_games.setChecked(DataStoreSingleton.getInstance(this).getBoolValue("HIDE_OWNED_GAMES"));
        switch_hide_owned_games.setOnCheckedChangeListener((view, state)->{
            DataStoreSingleton.getInstance(this).setBoolValue("HIDE_OWNED_GAMES", state);
        });
    }

    public Credit[] getCredits(){
        return new Credit[]{
                new Credit("Home image by Leni Kauffman on blush.design", "https://blush.design/fr"),
                new Credit("Loading image by Storyset", "https://storyset.com/"),
                new Credit("Image not found by Freepik", "https://www.freepik.com/"),
                new Credit("Arrow icon by Freepik", "www.freepik.com/icon/up-arrow_11997207"),
                new Credit("Gson for JSON parsing"),
                new Credit("okhttp3 for network requests"),
                new Credit("Glide for image loading"),
                new Credit("Room for database"),
                new Credit("DataStore for settings"),
                new Credit("SteamGridDB for some game images"),
                new Credit("MultiRowsRadioGroup to display radio button in multiple lines by linfaxin", "https://github.com/linfaxin/MultiRowsRadioGroup")
        };
    }
}