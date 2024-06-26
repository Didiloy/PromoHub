package com.github.didiloy.promohub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.didiloy.promohub.database.AppDatabase;
import com.github.didiloy.promohub.results.AllDealsActivity;
import com.github.didiloy.promohub.results.OwnedDealsActivity;
import com.github.didiloy.promohub.results.SavedDealsActivity;
import com.github.didiloy.promohub.search.SearchActivity;
import com.github.didiloy.promohub.select_store.SelectStoreActivity;
import com.github.didiloy.promohub.settings.DataStoreSingleton;
import com.github.didiloy.promohub.settings.SettingsActivity;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    public static Logger logger = Logger.getLogger("PromoHubLogger");
    public Context context;
    Button button_quit;
    Button button_customize_search;

    Button button_show_all_deals;
    Button search;
    Button show_saved_deals;
    Button parameters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = getApplicationContext();
        button_quit = findViewById(R.id.button_quit);
        button_customize_search = findViewById(R.id.button_customize_search);
        button_show_all_deals = findViewById(R.id.button_show_all_deals);
        search = findViewById(R.id.button_search);
        show_saved_deals = findViewById(R.id.button_show_saved_deals);
        parameters = findViewById(R.id.button_parameter);

        new Thread(() -> AppDatabase.Initialize(context)).start();
        //Initialize datastore settings
        DataStoreSingleton.getInstance(this);
    }

    public void onButtonCustomizeSearchClick(View view) {
        Intent intent = new Intent(this, SelectStoreActivity.class);
        startActivity(intent);
    }

    public void onButtonShowAllDealsClick(View view) {
        Intent intent = new Intent(this, AllDealsActivity.class);
        startActivity(intent);
    }

    public void onButtonSearchClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void onButtonSettingsClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onButtonShowSavedDealsClick(View view) {
        Intent intent = new Intent(this, SavedDealsActivity.class);
        startActivity(intent);
    }

    public void onButtonShowOwnedGames(View view) {
        Intent intent = new Intent(this, OwnedDealsActivity.class);
        startActivity(intent);
    }

    public void onButtonQuitClick(View view) {
        finish();
    }
}