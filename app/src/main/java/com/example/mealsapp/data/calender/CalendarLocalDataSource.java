package com.example.mealsapp.data.calender;

import com.example.mealsapp.data.database.planner_dao.PlannedMeal;
import com.example.mealsapp.data.database.planner_dao.PlannedMealDao;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class CalendarLocalDataSource {

    private final PlannedMealDao dao;

    public CalendarLocalDataSource(PlannedMealDao dao) {
        this.dao = dao;
    }

    public Completable insertMeal(PlannedMeal meal) {
        return dao.insert(meal);
    }

    public Flowable<List<PlannedMeal>> getAllMeals() {
        return dao.getAll();
    }

    public Completable deleteMeal(String id) {
        return dao.delete(id);
    }
    public Completable clearAll() {
        return dao.clearAll();
    }
}
