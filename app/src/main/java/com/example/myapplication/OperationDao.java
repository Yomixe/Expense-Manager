package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao

public interface OperationDao {


    @Insert
    void insert(Operation operation);

    @Update
    void update(Operation operation);

    @Delete
    void delete(Operation operation);

    @Query("DELETE FROM operations")
    void deleteAll();
    @Query("SELECT * from operations ORDER BY date DESC")
    LiveData<List<Operation>> getAllOperations();

    @Query("SELECT round(sum(price),2) from operations WHERE DATE(date)== DATE('now')")
    LiveData<Double> getDailyPrice();

    @Query("SELECT round(sum(price),2) from operations WHERE STRFTIME('%m',date)== STRFTIME('%m','now')")
    LiveData<Double> getMonthlyPrice();

    @Query("SELECT round(sum(price),2) from operations WHERE STRFTIME('%Y',date)== STRFTIME('%Y','now')")
    LiveData<Double> getYearlyPrice();

    @Query("SELECT round(sum(price),2) as total,category from operations GROUP BY category")
    LiveData <List<TotalPriceByCategory>> getPriceByCategories();




}
