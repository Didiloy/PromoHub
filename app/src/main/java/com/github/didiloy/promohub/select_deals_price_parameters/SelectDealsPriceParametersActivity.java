package com.github.didiloy.promohub.select_deals_price_parameters;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.didiloy.promohub.R;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SelectDealsPriceParametersActivity extends AppCompatActivity {

    RangeSlider slider_number_of_deals;
    TextView max_price_value;
    TextView min_price_value;
    SwitchMaterial switch_price;

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

        slider_number_of_deals = findViewById(R.id.slider_number_of_deals);
        max_price_value = findViewById(R.id.max_price_value);
        min_price_value = findViewById(R.id.min_price_value);
        switch_price = findViewById(R.id.switch_all_prices);

        slider_number_of_deals.setValues(0f, 25f);
        min_price_value.setText(String.valueOf(Math.round(slider_number_of_deals.getValues().get(0))));
        max_price_value.setText(String.valueOf(Math.round(slider_number_of_deals.getValues().get(1))));
        slider_number_of_deals.addOnChangeListener((slider, value, fromUser) -> {
            min_price_value.setText(String.valueOf(Math.round(slider.getValues().get(0))));
            max_price_value.setText(String.valueOf(Math.round(slider.getValues().get(1))));
        });

        switch_price.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                slider_number_of_deals.setEnabled(false);
                min_price_value.setText("");
                max_price_value.setText("");
                slider_number_of_deals.setVisibility(TextView.GONE);
                min_price_value.setVisibility(TextView.GONE);
                max_price_value.setVisibility(TextView.GONE);
            } else {
                slider_number_of_deals.setEnabled(true);
                slider_number_of_deals.setVisibility(TextView.VISIBLE);
                min_price_value.setVisibility(TextView.VISIBLE);
                max_price_value.setVisibility(TextView.VISIBLE);
                min_price_value.setText(String.valueOf(Math.round(slider_number_of_deals.getValues().get(0))));
                max_price_value.setText(String.valueOf(Math.round(slider_number_of_deals.getValues().get(1))));
            }
        });

    }
}