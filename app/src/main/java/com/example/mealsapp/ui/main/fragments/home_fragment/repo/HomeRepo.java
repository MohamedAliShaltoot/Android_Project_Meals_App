package com.example.mealsapp.ui.main.fragments.home_fragment.repo;


import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Callback;

public interface HomeRepo {

    Single<CategoriesResponse> getCategories();
    Single<MealsResponse> getRandomMeal();

}

