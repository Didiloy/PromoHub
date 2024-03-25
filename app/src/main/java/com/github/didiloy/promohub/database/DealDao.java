package com.github.didiloy.promohub.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DealDao {
    @Insert
    void insertDeal(DealEntity deals);

    @Delete
    void deleteDeal(DealEntity deal);

    @Query("SELECT * FROM deals")
    List<DealEntity> getAll();
}
