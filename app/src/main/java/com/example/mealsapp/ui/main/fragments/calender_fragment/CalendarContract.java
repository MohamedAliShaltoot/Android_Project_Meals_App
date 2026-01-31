package com.example.mealsapp.ui.main.fragments.calender_fragment;

import com.example.mealsapp.data.database.planner_dao.PlannedMeal;

import java.util.List;

public interface CalendarContract {

    interface View {
        void showMeals(List<PlannedMeal> meals);
        void showEmpty();
    }

    interface Presenter {
        void loadMeals();
        void deleteMeal(PlannedMeal meal);
        void clear();
    }
}
