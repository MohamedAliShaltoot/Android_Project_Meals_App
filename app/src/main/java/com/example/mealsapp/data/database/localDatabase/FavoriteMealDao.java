package com.example.mealsapp.data.database.localDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMeal meal);

    @Delete
    void delete(FavoriteMeal meal);


    @Query("SELECT * FROM favorite_meals")
    LiveData<List<FavoriteMeal>> getAllFavorites();

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal=:id)")
    boolean isFavorite(String id);
}


