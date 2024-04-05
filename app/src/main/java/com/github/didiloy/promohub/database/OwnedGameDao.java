package com.github.didiloy.promohub.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OwnedGameDao {
    @Insert
    void insertOwnedGame(OwnedGame game);

    @Delete
    void deleteOwnedGame(OwnedGame game);

    @Query("SELECT * FROM owned_games")
    List<OwnedGame> getAll();
}
