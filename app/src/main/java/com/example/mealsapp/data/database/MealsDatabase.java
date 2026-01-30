package com.example.mealsapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealsapp.data.database.dao.FavoriteMeal;
import com.example.mealsapp.data.database.dao.FavoriteMealDao;

@Database(entities = {FavoriteMeal.class}, version = 2)
public abstract class MealsDatabase extends RoomDatabase {

    private static MealsDatabase instance;

    public abstract FavoriteMealDao favoriteMealDao();

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

