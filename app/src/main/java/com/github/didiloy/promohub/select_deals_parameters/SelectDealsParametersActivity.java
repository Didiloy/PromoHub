package com.github.didiloy.promohub.select_deals_parameters;

import android.os.Bundle;
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
}