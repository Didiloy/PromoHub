package com.github.didiloy.promohub.results;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.api.CheapShark;
import com.github.didiloy.promohub.api.Deal;

import java.util.Objects;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    Context context;
    Deal[] deals;

    public DealAdapter(Context context, Deal[] stores) {
        this.context = context;
        this.deals = stores;
    }


    @NonNull
    @Override
    public DealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.deal_item_view, parent, false);
        return new DealAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealAdapter.ViewHolder holder, int position) {
        //textView
        String title;
        if(deals[position].title == null || Objects.equals(deals[position].title, "")){ //pour la recherche on ne recoit pas les meme résultats (voir https://apidocs.cheapshark.com/#c58ecff8-ee51-2901-f263-8606e8dc281e)
            title = deals[position].external;
        } else {
            title = deals[position].title;
        }
        holder.textview_game_title.setText(title);
        holder.textview_deal_store.setText(CheapShark.getStoreNameById(deals[position].storeID));

        String price;
        if(deals[position].salePrice == null || Objects.equals(deals[position].salePrice, "")){ //pour la recherche
            price = context.getString(R.string.cheapest) + " " + deals[position].cheapest;
        } else {
            price = deals[position].salePrice;
        }
        holder.deal_price.setText(price + "$");

        //image
        String imageUrl = deals[position].thumb;
        Glide.with(holder.itemView.getContext()).load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.image_not_found)
                .into(holder.imageView_game_hero);

        holder.cardView_deal_item.setOnClickListener(v -> {
            Intent intent = new Intent(context, DealDetail.class);
            intent.putExtra("deal", deals[position]);
            startActivity(context, intent, null);
        });
    }

    @Override
    public int getItemCount() {
        return deals.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_game_hero;
        TextView textview_game_title;
        TextView textview_deal_store;
        TextView deal_price;
        CardView cardView_deal_item;

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            cardView_deal_item = itemView.findViewById(R.id.cardView_deal_item);
            imageView_game_hero = itemView.findViewById(R.id.imageView_game_hero);
            textview_game_title = itemView.findViewById(R.id.textview_game_title);
            textview_deal_store = itemView.findViewById(R.id.textview_deal_store);
            deal_price = itemView.findViewById(R.id.deal_price);
        }
    }
}