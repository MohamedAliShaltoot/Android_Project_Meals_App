package com.example.mealsapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealsapp.data.database.dao.FavoriteMeal;
import com.example.mealsapp.data.database.dao.FavoriteMealDao;
import com.example.mealsapp.data.database.planner_dao.PlannedMeal;
import com.example.mealsapp.data.database.planner_dao.PlannedMealDao;

@Database(entities = {FavoriteMeal.class, PlannedMeal.class }, version = 3)
public abstract class MealsDatabase extends RoomDatabase {

    private static MealsDatabase instance;

    public abstract FavoriteMealDao favoriteMealDao();
    public abstract PlannedMealDao plannedMealDao();
    public static synchronized MealsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MealsDatabase.class,
                            "meals_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }
}

