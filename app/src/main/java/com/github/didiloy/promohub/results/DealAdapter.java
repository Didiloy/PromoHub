package com.github.didiloy.promohub.results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.didiloy.promohub.MainActivity;
import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.api.CheapShark;
import com.github.didiloy.promohub.api.Deal;

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
        holder.textview_game_title.setText(deals[position].title);
        holder.textview_deal_store.setText(CheapShark.getStoreNameById(deals[position].storeID));
        holder.deal_price.setText(deals[position].salePrice + "€");

        //image
        String imageUrl = deals[position].thumb;
        Glide.with(holder.itemView.getContext()).load(imageUrl)
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(holder.imageView_game_hero);

        //button
//        holder.button_view_deal.setOnClickListener(v -> {
//            MainActivity.logger.info("Button clicked");
//        });
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

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            imageView_game_hero = itemView.findViewById(R.id.imageView_game_hero);
            textview_game_title = itemView.findViewById(R.id.textview_game_title);
            textview_deal_store = itemView.findViewById(R.id.textview_deal_store);
            deal_price = itemView.findViewById(R.id.deal_price);
        }
    }
}