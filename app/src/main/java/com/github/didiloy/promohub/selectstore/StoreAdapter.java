package com.github.didiloy.promohub.selectstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        holder.textview_store_name.setText(stores[position].storeName);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(stores[position].isChecked);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> stores[position].isChecked = isChecked);
        String imageUrl = CheapShark.IMG_BASE_URL + stores[position].images.get("logo");
        Glide.with(holder.itemView.getContext()).load(imageUrl).placeholder(R.drawable.image_not_found).error(R.drawable.image_not_found).into(holder.imageView_logo);
    }

    @Override
    public int getItemCount() {
        return stores.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_logo;
        TextView textview_store_name;
        CheckBox checkBox;

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            imageView_logo = itemView.findViewById(R.id.imageView_logo);
            textview_store_name = itemView.findViewById(R.id.textview_store_name);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
