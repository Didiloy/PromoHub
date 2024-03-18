package com.github.didiloy.promohub.select_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.api.CheapShark;
import com.github.didiloy.promohub.api.Store;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    Context context;
    Store[] stores;

    public StoreAdapter(Context context, Store[] stores) {
        this.context = context;
        this.stores = stores;
    }


    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.store_select_item, parent, false);
        return new StoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {
        //textview
        holder.textview_store_name.setText(stores[position].storeName);

        //checkbox
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(stores[position].isChecked);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> stores[position].isChecked = isChecked);

        //image
        String imageUrl = CheapShark.IMG_BASE_URL + stores[position].images.get("logo");
        Glide.with(holder.itemView.getContext()).load(imageUrl)
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(holder.imageView_logo);

        //cardview
        holder.cardView.setOnClickListener(v -> {
            holder.checkBox.setChecked(!holder.checkBox.isChecked());
            stores[position].isChecked = holder.checkBox.isChecked();
        });
    }

    @Override
    public int getItemCount() {
        return stores.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_logo;
        TextView textview_store_name;
        CheckBox checkBox;
        CardView cardView;

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            imageView_logo = itemView.findViewById(R.id.imageView_logo);
            textview_store_name = itemView.findViewById(R.id.textview_store_name);
            checkBox = itemView.findViewById(R.id.checkBox);
            cardView = itemView.findViewById(R.id.cardView_store_select_item);
        }
    }
}
