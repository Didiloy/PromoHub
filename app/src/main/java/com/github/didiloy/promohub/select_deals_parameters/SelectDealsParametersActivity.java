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
import com.github.didiloy.promohub.settings.DataStoreSingleton;
import com.google.android.material.slider.Slider;

import java.util.Objects;

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

    TextView text_activate_auto_save;

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
        text_activate_auto_save = findViewById(R.id.text_activate_auto_save);

        text_activate_auto_save.setVisibility(DataStoreSingleton.getInstance(this).getBoolValue("SAVE_SETTINGS") ? View.GONE : View.VISIBLE);


        if (DataStoreSingleton.getInstance(this).getIntValue("NUMBER_OF_DEALS") != -1) {
            slider_number_of_deals.setValue(DataStoreSingleton.getInstance(this).getIntValue("NUMBER_OF_DEALS"));
        }
        if (DataStoreSingleton.getInstance(this).getIntValue("MAX_AGE_OF_DEALS") != -1) {
            slider_max_age_of_deals.setValue(DataStoreSingleton.getInstance(this).getIntValue("MAX_AGE_OF_DEALS"));
        }
        textview_number_of_deals_value.setText(String.valueOf((int) slider_number_of_deals.getValue()));
        textview_max_age_of_deals_value.setText(String.valueOf((int) slider_max_age_of_deals.getValue()));

        slider_number_of_deals.addOnChangeListener((slider, value, fromUser) -> {
            textview_number_of_deals_value.setText(String.valueOf((int) value));
        });
        slider_max_age_of_deals.addOnChangeListener((slider, value, fromUser) -> {
            textview_max_age_of_deals_value.setText(String.valueOf((int) value));
        });

        setSortBy();
        setSortOrder();
    }

    public void onButtonNextClicked(View v) {
        Intent intent = new Intent(this, SelectDealsPriceParametersActivity.class);
        intent.putExtra("selectedStores", selectedStoresFromPreviousActivity);
        intent.putExtra("numberOfDeals", (int) slider_number_of_deals.getValue());
        intent.putExtra("maxAgeOfDeals", (int) slider_max_age_of_deals.getValue());
        intent.putExtra("sortBy", getSortBy());
        intent.putExtra("sortOrder", getSortOrder());

        //sauvegarder les paramètres si c'est coché
        if (DataStoreSingleton.getInstance(this).getBoolValue("SAVE_SETTINGS")) {
            DataStoreSingleton.getInstance(this).setIntValue("NUMBER_OF_DEALS", (int) slider_number_of_deals.getValue());
            DataStoreSingleton.getInstance(this).setIntValue("MAX_AGE_OF_DEALS", (int) slider_max_age_of_deals.getValue());
            DataStoreSingleton.getInstance(this).setStringValue("SORT_BY", getSortBy());
            DataStoreSingleton.getInstance(this).setStringValue("SORT_ORDER", getSortOrder());
        }
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

    private void setSortBy() {
        if (Objects.equals(DataStoreSingleton.getInstance(this).getStringValue("SORT_BY"), "null")) {
            radio_button_DealRating.setChecked(true);
            return;
        }
        switch (DataStoreSingleton.getInstance(this).getStringValue("SORT_BY")) {
            case "Title":
                radio_button_Title.setChecked(true);
                break;
            case "Savings":
                radio_button_Savings.setChecked(true);
                break;
            case "Price":
                radio_button_Price.setChecked(true);
                break;
            case "Store":
                radio_button_Store.setChecked(true);
                break;
            case "Metacritic":
                radio_button_Metacritic.setChecked(true);
                break;
            case "Reviews":
                radio_button_Reviews.setChecked(true);
                break;
            case "Release":
                radio_button_Release.setChecked(true);
                break;
            case "Recent":
                radio_button_Recent.setChecked(true);
                break;
            default: //dealrating
                radio_button_DealRating.setChecked(true);
                break;
        }
    }

    private String getSortOrder() {
        if (radio_button_Ascending.isChecked()) return "0";
        if (radio_button_Descending.isChecked()) return "1";
        return ""; //default
    }

    private void setSortOrder() {
        if (Objects.equals(DataStoreSingleton.getInstance(this).getStringValue("SORT_ORDER"), "null")) {
            radio_button_Descending.setChecked(true);
            return;
        }
        if (DataStoreSingleton.getInstance(this).getStringValue("SORT_ORDER").equals("1")) {
            radio_button_Descending.setChecked(true);
        } else {
            radio_button_Ascending.setChecked(true);
        }

    }
}