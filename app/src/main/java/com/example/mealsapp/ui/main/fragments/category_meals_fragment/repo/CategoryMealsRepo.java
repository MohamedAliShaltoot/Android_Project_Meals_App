package com.example.mealsapp.ui.main.fragments.category_meals_fragment.repo;

import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Single;

public interface CategoryMealsRepo {
    Single<MealsResponse> getMealsByCategory(String category);
}

