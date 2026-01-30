package com.example.mealsapp.ui.main.fragments.meals_details_fragment.repo;

import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MealDetailsRepository {

    Single<MealsResponse> getMealDetails(String mealId);

    Single<Boolean> isFavorite(String mealId);

    Completable addFavorite(FavoriteMeal meal);

    Completable removeFavorite(FavoriteMeal meal);
}

