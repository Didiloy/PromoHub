package com.github.didiloy.promohub.results;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.github.didiloy.promohub.MainActivity;
import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.api.CheapShark;
import com.github.didiloy.promohub.api.Deal;
import com.github.didiloy.promohub.api.SteamGridDb;
import com.github.didiloy.promohub.database.AppDatabase;
import com.github.didiloy.promohub.database.DealDao;
import com.github.didiloy.promohub.database.DealEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class DealDetail extends AppCompatActivity {

    ImageView imageView_game_image;
    TextView textView_game_title;
    TextView textView_game_store;
    TextView textView_game_price;
    TextView textView_game_old_price;
    RatingBar game_ratingbar_metacritic;
    TextView game_textview_steam_rating;
    Button button_view_deal;
    Button button_save_deal;
    CardView cardView_price;
    ImageView game_grid_image;
    Button own_game_button;
    Deal deal;

    String title;

    Button share_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deal_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        if (intent.getParcelableExtra("deal") == null) {
            MainActivity.logger.severe("DealDetail: deal is null");
            Toast.makeText(this, "Deal is null", Toast.LENGTH_SHORT).show();
            return;
        }
        deal = intent.getParcelableExtra("deal");

        imageView_game_image = findViewById(R.id.game_image);
        textView_game_title = findViewById(R.id.game_title);
        textView_game_store = findViewById(R.id.game_store);
        textView_game_price = findViewById(R.id.game_price);
        textView_game_old_price = findViewById(R.id.game_textview_old_price);
        game_ratingbar_metacritic = findViewById(R.id.game_ratingbar_metacritic);
        game_textview_steam_rating = findViewById(R.id.game_textview_steam_rating);
        button_view_deal = findViewById(R.id.button_view_deal_in_browser);
        button_save_deal = findViewById(R.id.button_save_deal);
        cardView_price = findViewById(R.id.cardView6);
        game_grid_image = findViewById(R.id.game_grid_image);
        share_button = findViewById(R.id.share_button);
        own_game_button = findViewById(R.id.own_game_button);

        cardView_price.setCardElevation(0);
        title = deal.title;
        if (title == null || title.isEmpty()) { //pour la recherche on ne recoit pas les meme résultats (voir https://apidocs.cheapshark.com/#c58ecff8-ee51-2901-f263-8606e8dc281e)
            title = deal.external;
        }
        textView_game_title.setText(title);
        textView_game_store.setText(CheapShark.getStoreNameById(deal.storeID));
        String price;
        if (deal.salePrice == null || Objects.equals(deal.salePrice, "")) { //pour la recherche
            price = deal.cheapest;
        } else {
            price = deal.salePrice;
        }
        textView_game_price.setText(price + "$");
        textView_game_old_price.setText(deal.normalPrice + "$");
        textView_game_old_price.setPaintFlags(textView_game_old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        int metacriticScore;
        if (deal.metacriticScore == 0) metacriticScore = 0;
        else {
            metacriticScore = deal.metacriticScore > 90 ? 5 : 5 - (100 / deal.metacriticScore);
        }
        game_ratingbar_metacritic.setRating(metacriticScore);
        game_textview_steam_rating.setText('"' + deal.steamRatingText + '"');
        setButtonSaveDeal();

        button_view_deal.setOnClickListener(v -> {
            String dealId = deal.dealID;
            if (dealId == null || dealId.isEmpty()) { //si on vient de la recherche
                dealId = deal.cheapestDealID;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(CheapShark.REDIRECT_BASE_URL + dealId));
            startActivity(browserIntent);
        });

        button_save_deal.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getInstance();
            DealDao dealDao = db.dealDao();
            if (deal.isSaved) {
                new Thread(() -> {
                    dealDao.deleteDeal(DealEntity.fromDeal(deal));
                    deal.isSaved = false;
                    runOnUiThread(() -> {
                        setButtonSaveDeal();
                        Toast.makeText(this, getString(R.string.deal_deleted), Toast.LENGTH_SHORT).show();
                    });
                }).start();
            } else {
                new Thread(() -> {
                    deal.isSaved = true;
                    deal.saved_date = getCurrentDate();
                    dealDao.insertDeal(DealEntity.fromDeal(deal));
                    runOnUiThread(() -> {
                        setButtonSaveDeal();
                        Toast.makeText(this, getString(R.string.deal_saved), Toast.LENGTH_SHORT).show();
                    });
                }).start();
            }
        });

        own_game_button.setOnClickListener(v -> {
            new Thread(() -> {
                //TODO
                runOnUiThread(() -> {
                    Toast.makeText(this, getString(R.string.deal_saved), Toast.LENGTH_SHORT).show();
                });
            }).start();
        });

        String imageUrl = deal.thumb;
        new Thread(() -> {
            String heroe_url = SteamGridDb.getGameHero(SteamGridDb.getGameIDByName(title));
            if (heroe_url == null) heroe_url = imageUrl;
            String finalHeroe_url = heroe_url;
            runOnUiThread(() -> Glide.with(this).load(finalHeroe_url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.image_not_found)
                    .into(imageView_game_image));
        }).start();
        new Thread(() -> {
            String grid_url = SteamGridDb.getGameGrid(SteamGridDb.getGameIDByName(title));
            if (grid_url == null) grid_url = imageUrl;
            String finalGrid_url = grid_url;
            runOnUiThread(() -> Glide.with(this).load(finalGrid_url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.image_not_found)
                    .into(game_grid_image));
        }).start();

        share_button.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + "\n" + CheapShark.REDIRECT_BASE_URL + deal.dealID);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_using)));
        });
    }

    public void setButtonSaveDeal() {
        if (deal.isSaved) {
            button_save_deal.setText(getString(R.string.delete_saved_deal));
            button_save_deal.setBackgroundColor(getTheme().obtainStyledAttributes(new int[]{com.google.android.material.R.attr.colorError}).getColor(0, 0));
        } else {
            button_save_deal.setText(getString(R.string.save_deal));
            button_save_deal.setBackgroundColor(getTheme().obtainStyledAttributes(new int[]{com.google.android.material.R.attr.colorSecondary}).getColor(0, 0));

        }
    }

    public String getCurrentDate(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return today.format(formatter);
    }
}