package com.github.didiloy.promohub.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DealEntity.class, OwnedGame.class},
        version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DealDao dealDao();
    public abstract OwnedGameDao ownedGameDao();

    private static final String DATABASE_NAME = "PromoHub.db";
    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase Initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    AppDatabase.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public static AppDatabase getInstance() {
        return INSTANCE;
    }
}
