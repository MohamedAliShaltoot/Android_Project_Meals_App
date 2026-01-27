package com.example.mealsapp.ui.main.fragments.home_fragment.repo;


import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;

import retrofit2.Callback;

public class HomeRepoImp implements HomeRepo {

    @Override
    public void getCategories(Callback<CategoriesResponse> callback) {
        RetrofitClient.getApi().getCategories().enqueue(callback);
    }

    @Override
    public void getRandomMeal(Callback<MealsResponse> callback) {
        RetrofitClient.getApi().getRandomMeal().enqueue(callback);
    }
}

