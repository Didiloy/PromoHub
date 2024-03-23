package com.github.didiloy.promohub.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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
import com.github.didiloy.promohub.results.DealAdapter;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class SearchActivity extends AppCompatActivity {

    Deal[] deals;
    RecyclerView recycler_view_results;
    DealAdapter dealAdapter;
    ProgressBar progressBar;
    TextInputEditText text_input_search;

    Button search_button;
    SwitchMaterial switch_exact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recycler_view_results = findViewById(R.id.recycler_view_results_search);
        progressBar = findViewById(R.id.progressBar_search);
        text_input_search = findViewById(R.id.text_input_search);
        search_button = findViewById(R.id.search_activity_button_search);
        switch_exact = findViewById(R.id.switch_exact);

        progressBar.setVisibility(ProgressBar.GONE);

        text_input_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onSearchButtonClick(v);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        text_input_search.requestFocus();
    }

    public void onSearchButtonClick(View view) {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        new Thread(() -> {
            if(text_input_search.getText() == null || text_input_search.getText().toString().isEmpty()){
                MainActivity.logger.severe("Empty search query");
                Toast toast = Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            deals = CheapShark.getDealsByGameName(text_input_search.getText().toString(), switch_exact.isChecked());
            if (deals == null) {
                MainActivity.logger.severe("Failed to fetch deals");
                Toast toast = Toast.makeText(this, "Failed to fetch deals", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            runOnUiThread(() -> {
                text_input_search.setText("");
                progressBar.setVisibility(ProgressBar.GONE);
                dealAdapter = new DealAdapter(this, deals);
                recycler_view_results.setAdapter(dealAdapter);
                recycler_view_results.setLayoutManager(new LinearLayoutManager(this));
            });
        }).start();
    }
}