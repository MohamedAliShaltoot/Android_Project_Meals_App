package com.example.mealsapp.data.calender;


import com.example.mealsapp.data.database.planner_dao.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;


public class CalendarSyncManager {

    private final CalendarLocalDataSource local;
    private final FirestoreCalendarRepository remote;

    public CalendarSyncManager(
            CalendarLocalDataSource local,
            FirestoreCalendarRepository remote
    ) {
        this.local = local;
        this.remote = remote;
    }

    public Completable addMeal(PlannedMeal meal) {
        return local.insertMeal(meal)
                .andThen(remote.addMeal(meal));
    }

    public Completable removeMeal(PlannedMeal meal) {
        return local.deleteMeal(meal.id)
                .andThen(remote.removeMeal(meal.id));
    }

public Completable syncFromFirestore() {
    return local.clearAll()
            .andThen(remote.getAllMeals())
            .flatMapCompletable(meals -> insertMealsSequentially(meals));
}

    private Completable insertMealsSequentially(List<PlannedMeal> meals) {
        Completable completable = Completable.complete();
        for (PlannedMeal meal : meals) {
            completable = completable.andThen(local.insertMeal(meal));
        }
        return completable;
    }

    public Completable clearLocal() {
        return local.clearAll();
    }
}
