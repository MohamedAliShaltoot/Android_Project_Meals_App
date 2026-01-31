package com.example.mealsapp.data.database.planner_dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jspecify.annotations.NonNull;


@Entity(tableName = "planned_meals")
public class PlannedMeal {

    @PrimaryKey
    @NonNull
    @androidx.annotation.NonNull
    public String id;

    public String mealId;
    public String mealName;
    public String mealThumb;

    public long date;
    public PlannedMeal() {
    }
    public PlannedMeal(@NonNull String id,
                       String mealId,
                       String mealName,
                       String mealThumb,
                       long date) {
        this.id = id;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealThumb = mealThumb;
        this.date = date;
    }
}
