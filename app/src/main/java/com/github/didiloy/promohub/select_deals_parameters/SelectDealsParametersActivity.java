package com.github.didiloy.promohub.select_deals_parameters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.didiloy.promohub.MainActivity;
import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.select_deals_price_parameters.SelectDealsPriceParametersActivity;
import com.google.android.material.slider.Slider;

public class SelectDealsParametersActivity extends AppCompatActivity {

    TextView textview_number_of_deals_value;
    Slider slider_number_of_deals;
    TextView textview_max_age_of_deals_value;
    Slider slider_max_age_of_deals;

    RadioGroup radio_group_sort_by;

    RadioButton radio_button_DealRating;
    RadioButton radio_button_Title;

    RadioButton radio_button_Savings;
    RadioButton radio_button_Price;
    RadioButton radio_button_Store;
    RadioButton radio_button_Metacritic;
    RadioButton radio_button_Reviews;
    RadioButton radio_button_Release;
    RadioButton radio_button_Recent;
    RadioButton radio_button_Ascending;
    RadioButton radio_button_Descending;
    String selectedStoresFromPreviousActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_deals_parameters);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //get the selected stores from the previous activity
        if (getIntent().getStringExtra("selectedStores") != null)
            selectedStoresFromPreviousActivity = getIntent().getStringExtra("selectedStores");
        else selectedStoresFromPreviousActivity = "";

        textview_number_of_deals_value = findViewById(R.id.textview_number_of_deals_value);
        slider_number_of_deals = findViewById(R.id.slider_number_of_deals);
        radio_group_sort_by = findViewById(R.id.radio_group_sort_by);
        radio_button_DealRating = findViewById(R.id.radio_button_DealRating);
        radio_button_Title = findViewById(R.id.radio_button_Title);
        radio_button_Savings = findViewById(R.id.radio_button_Savings);
        radio_button_Price = findViewById(R.id.radio_button_Price);
        radio_button_Store = findViewById(R.id.radio_button_Store);
        radio_button_Metacritic = findViewById(R.id.radio_button_Metacritic);
        radio_button_Reviews = findViewById(R.id.radio_button_Reviews);
        radio_button_Release = findViewById(R.id.radio_button_Release);
        radio_button_Recent = findViewById(R.id.radio_button_Recent);
        radio_button_Ascending = findViewById(R.id.radio_button_Ascending);
        radio_button_Descending = findViewById(R.id.radio_button_Descending);
        textview_max_age_of_deals_value = findViewById(R.id.textview_max_age_of_deals_value);
        slider_max_age_of_deals = findViewById(R.id.slider_max_age_of_deals);


        textview_number_of_deals_value.setText(String.valueOf((int) slider_number_of_deals.getValue()));
        textview_max_age_of_deals_value.setText(String.valueOf((int) slider_max_age_of_deals.getValue()));

        slider_number_of_deals.addOnChangeListener((slider, value, fromUser) -> {
            textview_number_of_deals_value.setText(String.valueOf((int) value));
        });
        slider_max_age_of_deals.addOnChangeListener((slider, value, fromUser) -> {
            textview_max_age_of_deals_value.setText(String.valueOf((int) value));
        });

        radio_button_DealRating.setChecked(true);
        radio_button_Descending.setChecked(true);
    }

    public void onButtonNextClicked(View v) {
        Intent intent = new Intent(this, SelectDealsPriceParametersActivity.class);
        intent.putExtra("selectedStores", selectedStoresFromPreviousActivity);
        intent.putExtra("numberOfDeals", (int) slider_number_of_deals.getValue());
        intent.putExtra("maxAgeOfDeals", (int) slider_max_age_of_deals.getValue());
        intent.putExtra("sortBy", getSortBy());
        intent.putExtra("sortOrder", getSortOrder());
        startActivity(intent);
    }

    private String getSortBy() {
        int selectedId = radio_group_sort_by.getCheckedRadioButtonId();
        if (selectedId == -1) return "";

        //cannot use switch statement because the radio buttons are not final
        if (selectedId == radio_button_DealRating.getId()) return "DealRating";
        if (selectedId == radio_button_Title.getId()) return "Title";
        if (selectedId == radio_button_Savings.getId()) return "Savings";
        if (selectedId == radio_button_Price.getId()) return "Price";
        if (selectedId == radio_button_Store.getId()) return "Store";
        if (selectedId == radio_button_Metacritic.getId()) return "Metacritic";
        if (selectedId == radio_button_Reviews.getId()) return "Reviews";
        if (selectedId == radio_button_Release.getId()) return "Release";
        if (selectedId == radio_button_Recent.getId()) return "Recent";
        return ""; //default
    }

    private String getSortOrder(){
        if(radio_button_Ascending.isChecked()) return "0";
        if(radio_button_Descending.isChecked()) return "1";
        return ""; //default
    }
}