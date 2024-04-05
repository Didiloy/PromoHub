package com.github.didiloy.promohub.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.github.didiloy.promohub.api.Deal;

@Entity(tableName = "owned_games")
public class OwnedGame {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String name;
    public String thumb;

    @Ignore
    public static OwnedGame fromDeal(Deal deal){
        OwnedGame owned_game = new OwnedGame();
        owned_game.name = deal.title;
        owned_game.thumb = deal.thumb;
        return owned_game;
    }
}
