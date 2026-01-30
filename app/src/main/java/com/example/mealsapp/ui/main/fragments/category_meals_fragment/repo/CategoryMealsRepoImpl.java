package com.example.mealsapp.ui.main.fragments.category_meals_fragment.repo;


import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;

import io.reactivex.rxjava3.core.Single;

public class CategoryMealsRepoImpl implements CategoryMealsRepo {

    @Override
    public Single<MealsResponse> getMealsByCategory(String category) {
        return RetrofitClient.getApi().getMealsByCategory(category);
    }
}

