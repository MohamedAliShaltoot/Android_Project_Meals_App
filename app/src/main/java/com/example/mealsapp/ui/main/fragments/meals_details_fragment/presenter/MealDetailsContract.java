package com.example.mealsapp.ui.main.fragments.meals_details_fragment.presenter;

import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.utils.SnackType;

public interface MealDetailsContract {

    interface View {
        void showMeal(Meal meal);
        void showFavoriteState(boolean isFavorite);
        void showMessage(String message, SnackType type);
        void showError(String message);
        void showGuestView();
    }

    interface Presenter {
        void loadMeal(String mealId);
        void toggleFavorite();
        void clear();
    }
}

