package com.example.mealsapp.data.database.localDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoriteMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FavoriteMeal meal);

    @Delete
    Completable delete(FavoriteMeal meal);

    @Query("SELECT * FROM favorite_meals")
    Flowable<List<FavoriteMeal>> getAllFavorites();

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal=:id)")
    Single<Boolean> isFavorite(String id);

    @Query("DELETE FROM favorite_meals")
    Completable clearAll();

}


