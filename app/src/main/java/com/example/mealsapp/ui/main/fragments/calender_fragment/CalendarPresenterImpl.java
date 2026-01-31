package com.example.mealsapp.ui.main.fragments.calender_fragment;

import com.example.mealsapp.data.calender.CalendarSyncManager;
import com.example.mealsapp.data.database.planner_dao.PlannedMeal;
import com.example.mealsapp.data.database.planner_dao.PlannedMealDao;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalendarPresenterImpl implements CalendarContract.Presenter {

    private final PlannedMealDao dao;
    private final CalendarSyncManager syncManager;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private CalendarContract.View view;

    public CalendarPresenterImpl(
            CalendarContract.View view,
            PlannedMealDao dao,
            CalendarSyncManager syncManager
    ) {
        this.view = view;
        this.dao = dao;
        this.syncManager = syncManager;
    }

    @Override
    public void loadMeals() {
        disposable.add(
                dao.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(meals -> {
                            if (meals.isEmpty()) view.showEmpty();
                            else view.showMeals(meals);
                        })
        );
    }

    @Override
    public void deleteMeal(PlannedMeal meal) {
        disposable.add(
                syncManager.removeMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        );
    }

    @Override
    public void clear() {
        disposable.clear();
        view = null;
    }
}
