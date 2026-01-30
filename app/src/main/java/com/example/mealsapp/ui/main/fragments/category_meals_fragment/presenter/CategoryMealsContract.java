package com.example.mealsapp.ui.main.fragments.category_meals_fragment.presenter;

import com.example.mealsapp.data.model.Meal;
import java.util.List;

public interface CategoryMealsContract {

    interface View {
        void showMeals(List<Meal> meals);
        void showError(String message);
    }

    interface Presenter {
        void loadMeals(String category);
        void clear();
    }
}

