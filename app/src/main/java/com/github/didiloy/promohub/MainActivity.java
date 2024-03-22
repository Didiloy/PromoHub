package com.github.didiloy.promohub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.didiloy.promohub.results.AllDealsActivity;
import com.github.didiloy.promohub.select_deals_parameters.SelectDealsParametersActivity;
import com.github.didiloy.promohub.select_deals_price_parameters.SelectDealsPriceParametersActivity;
import com.github.didiloy.promohub.select_store.SelectStoreActivity;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    public static Logger logger = Logger.getLogger("PromoHubLogger");

    Button button_quit;
    Button button_customize_search;

    Button button_show_all_deals;

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
        button_quit = findViewById(R.id.button_quit);
        button_customize_search = findViewById(R.id.button_customize_search);
        button_show_all_deals = findViewById(R.id.button_show_all_deals);

    }

    public void onButtonCustomizeSearchClick(View view) {
        Intent intent = new Intent(this, SelectStoreActivity.class);
        startActivity(intent);
    }

    public void onButtonShowAllDealsClick(View view) {
        Intent intent = new Intent(this, AllDealsActivity.class);
        startActivity(intent);
    }

    public void onButtonQuitClick(View view) {
        finish();
    }
}