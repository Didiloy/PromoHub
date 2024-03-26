package com.github.didiloy.promohub.settings;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.didiloy.promohub.R;


public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder>{

    Context context;
    Credit[] credits;

    public CreditAdapter(Context context, Credit[] credits) {
        this.context = context;
        this.credits = credits;
    }
    @NonNull
    @Override
    public CreditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.credit_item_view, parent, false);
        return new CreditAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.ViewHolder holder, int position) {
        //textview
        holder.textView_credit.setText(credits[position].credit);

        if(credits[position].link == null){
            holder.credit_arrow.setVisibility(View.GONE);
        }

        holder.credit_item_view.setOnClickListener(v -> {
           if(credits[position].link != null){
               Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(credits[position].link));
               startActivity(context, browserIntent, null);
           }
        });
    }

    @Override
    public int getItemCount() {
        return credits.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_credit;
        ConstraintLayout credit_item_view;
        ImageView credit_arrow;

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            textView_credit = itemView.findViewById(R.id.credit_name);
            credit_item_view = itemView.findViewById(R.id.credit_item_view);
            credit_arrow = itemView.findViewById(R.id.credit_arrow);
        }
    }
}
