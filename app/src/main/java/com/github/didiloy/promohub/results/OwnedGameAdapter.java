package com.github.didiloy.promohub.results;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.didiloy.promohub.MainActivity;
import com.github.didiloy.promohub.R;
import com.github.didiloy.promohub.api.CheapShark;
import com.github.didiloy.promohub.api.Deal;
import com.github.didiloy.promohub.database.AppDatabase;
import com.github.didiloy.promohub.database.OwnedGame;
import com.github.didiloy.promohub.database.OwnedGameDao;

import java.util.ArrayList;
import java.util.Objects;

public class OwnedGameAdapter extends RecyclerView.Adapter<OwnedGameAdapter.ViewHolder> {

    Context context;
    ArrayList<OwnedGame> games;

    public OwnedGameAdapter(Context context, ArrayList<OwnedGame> games) {
        this.context = context;
        this.games = games;
    }


    @NonNull
    @Override
    public OwnedGameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.game_item_view, parent, false);
        return new OwnedGameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnedGameAdapter.ViewHolder holder, int position) {
        //textView
        holder.textview_game_title.setText(games.get(position).name);
        Glide.with(context).load(games.get(position).thumb).into(holder.imageView_game_hero);
        holder.button_delete.setOnClickListener(v -> {
            Thread th = new Thread(() -> {
                 OwnedGameDao ownedGameDao = AppDatabase.getInstance().ownedGameDao();
                 ownedGameDao.deleteOwnedGame(games.get(position));
            });
            th.start();
            try {
                th.join();
                games.remove(position);
                notifyItemRemoved(position);
            } catch (InterruptedException e) {
                MainActivity.logger.severe("Failed to delete game.");
                Toast.makeText(context, "Failed to delete game", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview_game_title;
        ImageView imageView_game_hero;
        ImageButton button_delete;

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            textview_game_title = itemView.findViewById(R.id.textview_game_title);
            imageView_game_hero = itemView.findViewById(R.id.imageView_game_hero);
            button_delete = itemView.findViewById(R.id.imageView2);
        }
    }
}