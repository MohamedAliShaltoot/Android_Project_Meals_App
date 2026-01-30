package com.example.mealsapp.data.meals;

import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.MealApiService;
import com.example.mealsapp.data.network.RetrofitClient;
import io.reactivex.rxjava3.core.Single;

public class MealsRemoteDataSource {

    private final MealApiService api;

    public MealsRemoteDataSource() {
        this.api = RetrofitClient.getApi();
    }

    public Single<MealsResponse> getMealDetails(String mealId) {
        return api.getMealDetails(mealId);
    }
}
