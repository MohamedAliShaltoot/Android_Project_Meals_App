package com.example.mealsapp.ui.main.fragments.home_fragment.repo;


import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;

import retrofit2.Callback;

public interface HomeRepo {
    void getCategories(Callback<CategoriesResponse> callback);
    void getRandomMeal(Callback<MealsResponse> callback);
}

