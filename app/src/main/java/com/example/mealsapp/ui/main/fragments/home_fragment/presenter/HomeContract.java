package com.example.mealsapp.ui.main.fragments.home_fragment;



import com.example.mealsapp.data.model.Category;
import com.example.mealsapp.data.model.Meal;

import java.util.List;

public interface HomeContract {

    interface View {
        void showCategories(List<Category> categories);
        void showRandomMeal(Meal meal);
        void showError(String message);
    }

    interface Presenter {
        void getCategories();
        void getRandomMeal();
        void onDestroy();
    }
}

