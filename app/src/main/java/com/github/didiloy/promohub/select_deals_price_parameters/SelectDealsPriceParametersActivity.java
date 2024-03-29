package com.github.didiloy.promohub.select_deals_price_parameters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.results.ResultsActivity;
import com.github.didiloy.promohub.settings.DataStoreSingleton;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SelectDealsPriceParametersActivity extends AppCompatActivity {

    RangeSlider slider_deal_price;
    TextView max_price_value;
    TextView min_price_value;
    SwitchMaterial switch_price;

    CheckBox checkBox_deal_parameter_aaa;
    CheckBox checkBox_deal_parameter_on_sale;

    RatingBar rating_metacritic;
    RatingBar rating_steam;
    String selectedStoresFromPreviousActivity;
    int numberOfDealsFromPreviousActivity;
    int maxAgeOfDealsFromPreviousActivity;
    String sortByFromPreviousActivity;
    String sortOrderFromPreviousActivity;
    TextView text_activate_auto_save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_deals_price_parameters);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get values from the previous activity
        if(getIntent().getStringExtra("selectedStores") != null)
            selectedStoresFromPreviousActivity = getIntent().getStringExtra("selectedStores");
        else selectedStoresFromPreviousActivity = "";
        numberOfDealsFromPreviousActivity = getIntent().getIntExtra("numberOfDeals", 0);
        maxAgeOfDealsFromPreviousActivity = getIntent().getIntExtra("maxAgeOfDeals", 0);
        if(getIntent().getStringExtra("sortBy") != null)
            sortByFromPreviousActivity = getIntent().getStringExtra("sortBy");
        else sortByFromPreviousActivity = "";
        if(getIntent().getStringExtra("sortOrder") != null)
            sortOrderFromPreviousActivity = getIntent().getStringExtra("sortOrder");
        else sortOrderFromPreviousActivity = "";

        //get the views from the layout
        slider_deal_price = findViewById(R.id.slider_deal_price);
        max_price_value = findViewById(R.id.max_price_value);
        min_price_value = findViewById(R.id.min_price_value);
        switch_price = findViewById(R.id.switch_all_prices);
        checkBox_deal_parameter_aaa = findViewById(R.id.checkBox_deal_parameter_aaa);
        checkBox_deal_parameter_on_sale = findViewById(R.id.checkBox_deal_parameter_on_sale);
        rating_metacritic = findViewById(R.id.rating_metacritic);
        rating_steam = findViewById(R.id.rating_steam);
        text_activate_auto_save = findViewById(R.id.text_activate_auto_save);


        text_activate_auto_save.setVisibility(DataStoreSingleton.getInstance(this).getBoolValue("SAVE_SETTINGS") ? View.GONE : View.VISIBLE);


        //set default values
        setSlider_deal_price();
        min_price_value.setText(String.valueOf(Math.round(slider_deal_price.getValues().get(0))));
        max_price_value.setText(String.valueOf(Math.round(slider_deal_price.getValues().get(1))));
        slider_deal_price.addOnChangeListener((slider, value, fromUser) -> {
            min_price_value.setText(String.valueOf(Math.round(slider.getValues().get(0))));
            max_price_value.setText(String.valueOf(Math.round(slider.getValues().get(1))));
        });

        switch_price.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                slider_deal_price.setEnabled(false);
                min_price_value.setText("");
                max_price_value.setText("");
                slider_deal_price.setVisibility(TextView.GONE);
                min_price_value.setVisibility(TextView.GONE);
                max_price_value.setVisibility(TextView.GONE);
            } else {
                slider_deal_price.setEnabled(true);
                slider_deal_price.setVisibility(TextView.VISIBLE);
                min_price_value.setVisibility(TextView.VISIBLE);
                max_price_value.setVisibility(TextView.VISIBLE);
                min_price_value.setText(String.valueOf(Math.round(slider_deal_price.getValues().get(0))));
                max_price_value.setText(String.valueOf(Math.round(slider_deal_price.getValues().get(1))));
            }
        });

        checkBox_deal_parameter_aaa.setChecked(DataStoreSingleton.getInstance(this).getBoolValue("AAA"));
        checkBox_deal_parameter_on_sale.setChecked(DataStoreSingleton.getInstance(this).getBoolValue("ON_SALE"));
        setRatings();

    }

    public void onButtonFinishClicked(View v) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("selectedStores", selectedStoresFromPreviousActivity);
        intent.putExtra("numberOfDeals", numberOfDealsFromPreviousActivity);
        intent.putExtra("maxAgeOfDeals", maxAgeOfDealsFromPreviousActivity);
        intent.putExtra("sortBy", sortByFromPreviousActivity);
        intent.putExtra("sortOrder", sortOrderFromPreviousActivity);
        if(switch_price.isChecked()){
            intent.putExtra("minPrice", 0);
            intent.putExtra("maxPrice", 50); //50 act the same as no limit
        } else {
            intent.putExtra("minPrice", Math.round(slider_deal_price.getValues().get(0)));
            intent.putExtra("maxPrice", Math.round(slider_deal_price.getValues().get(1)));
        }
        intent.putExtra("AAA", checkBox_deal_parameter_aaa.isChecked() ? 1 : 0);
        intent.putExtra("onSale", checkBox_deal_parameter_on_sale.isChecked() ? 1 : 0);
        int metacritic_value;
        int steam_value;
        if((int)rating_metacritic.getRating() == 5){
            metacritic_value = 90;
        }else {
            metacritic_value = 20 * (int) rating_metacritic.getRating();
        }
        if((int)rating_steam.getRating() == 5){
            steam_value = 90;
        }else {
            steam_value = 20 * (int) rating_steam.getRating();
        }
        intent.putExtra("metacritic", metacritic_value);
        intent.putExtra("steam", steam_value);

        //sauvegarder les paramètres si c'est coché
        if (DataStoreSingleton.getInstance(this).getBoolValue("SAVE_SETTINGS")) {
            if(switch_price.isChecked()){
                DataStoreSingleton.getInstance(this).setIntValue("MIN_PRICE", 0);
                DataStoreSingleton.getInstance(this).setIntValue("MAX_PRICE", 50);
            } else {
                DataStoreSingleton.getInstance(this).setIntValue("MIN_PRICE", Math.round(slider_deal_price.getValues().get(0)));
                DataStoreSingleton.getInstance(this).setIntValue("MAX_PRICE", Math.round(slider_deal_price.getValues().get(1)));
            }
            DataStoreSingleton.getInstance(this).setBoolValue("AAA", checkBox_deal_parameter_aaa.isChecked());
            DataStoreSingleton.getInstance(this).setBoolValue("ON_SALE", checkBox_deal_parameter_on_sale.isChecked());
            DataStoreSingleton.getInstance(this).setIntValue("RATING_METACRITIC", (int) rating_metacritic.getRating());
            DataStoreSingleton.getInstance(this).setIntValue("RATING_STEAM", (int) rating_steam.getRating());
        }

        startActivity(intent);
    }

    public void setSlider_deal_price(){
        slider_deal_price.setValues(0f, 25f);
        if (DataStoreSingleton.getInstance(this).getIntValue("MAX_PRICE") != -1){
            if (DataStoreSingleton.getInstance(this).getIntValue("MAX_PRICE") == 50){
                switch_price.setChecked(true);
                slider_deal_price.setEnabled(false);
                min_price_value.setText("");
                max_price_value.setText("");
                slider_deal_price.setVisibility(TextView.GONE);
                min_price_value.setVisibility(TextView.GONE);
                max_price_value.setVisibility(TextView.GONE);
                return;
            }
            int min = DataStoreSingleton.getInstance(this).getIntValue("MIN_PRICE");
            int max = DataStoreSingleton.getInstance(this).getIntValue("MAX_PRICE");
            slider_deal_price.setValues((float) min, (float) max);
        }
    }

    public void setRatings(){
        rating_metacritic.setRating(DataStoreSingleton.getInstance(this).getIntValue("RATING_METACRITIC") == -1 ? 0 : (float) DataStoreSingleton.getInstance(this).getIntValue("RATING_METACRITIC"));
        rating_steam.setRating(DataStoreSingleton.getInstance(this).getIntValue("RATING_STEAM") == -1 ? 0 : (float) DataStoreSingleton.getInstance(this).getIntValue("RATING_STEAM"));
    }
}