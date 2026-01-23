package com.example.mealsapp.data.database.localDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jspecify.annotations.NonNull;

@Entity(tableName = "favorite_meals")
public class FavoriteMeal {

    @PrimaryKey
    @androidx.annotation.NonNull
    public String idMeal;

    public String name;
    public String image;

    public FavoriteMeal(
            @androidx.annotation.NonNull String idMeal,
            String name,
            String image
    ) {
        this.idMeal = idMeal;
        this.name = name;
        this.image = image;
    }
}


