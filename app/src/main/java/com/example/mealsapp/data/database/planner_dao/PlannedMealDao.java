package com.example.mealsapp.data.database.planner_dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface PlannedMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PlannedMeal meal);

    @Query("SELECT * FROM planned_meals ORDER BY date ASC")
    Flowable<List<PlannedMeal>> getAll();

    @Query("DELETE FROM planned_meals WHERE id = :id")
    Completable delete(String id);
    @Query("DELETE FROM planned_meals")
    Completable clearAll();
}
