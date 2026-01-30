package com.example.mealsapp.data.home;

import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Single;
public interface HomeRepository {
    Single<CategoriesResponse> getCategories();
    Single<MealsResponse> getRandomMeal();
}

