package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "operations")

public class Operation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "price")
    private Double mPrice;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "date")
    private String mDate;

    @NonNull
    @ColumnInfo(name = "category")
    private String mCategory;


    public Operation(int id, @NonNull Double mPrice, @NonNull String mName, @NonNull String mDate, @NonNull String mCategory) {
        this.mId = id;
        this.mPrice = mPrice;
        this.mName = mName;
        this.mDate = mDate;
        this.mCategory = mCategory;

    }

    @Ignore
    public Operation(@NonNull Double mPrice, @NonNull String mName, @NonNull String mDate, @NonNull String mCategory) {

        this.mPrice = mPrice;
        this.mName = mName;
        this.mDate = mDate;
        this.mCategory = mCategory;
    }

    public int getId() {
        return mId;
    }

    public Double getPrice() {
        return this.mPrice;
    }

    public String getName() {
        return this.mName;
    }

    public String getDate() {
        return this.mDate;
    }

    public String getCategory() {
        return this.mCategory;
    }

    public String getStrPrice() {
        return this.mPrice.toString();
    }


}




