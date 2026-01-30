package com.example.mealsapp.data.database.dao;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jspecify.annotations.NonNull;

@Entity(tableName = "favorite_meals")
public class FavoriteMeal {

    @PrimaryKey
    @androidx.annotation.NonNull
    public String idMeal;
    public String name;
    public String image;
  // for firestore
    public FavoriteMeal() {
    }

    // Used by Room and app logic
    @Ignore
    public FavoriteMeal(
            @NonNull String idMeal,
            String name,
            String image
    ) {
        this.idMeal = idMeal;
        this.name = name;
        this.image = image;
    }

}


