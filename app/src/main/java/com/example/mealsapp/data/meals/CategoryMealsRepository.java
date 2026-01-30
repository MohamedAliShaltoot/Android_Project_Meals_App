package com.example.mealsapp.data.meals;

import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Single;

public interface CategoryMealsRepository {
    Single<MealsResponse> getMealsByCategory(String category);
}

