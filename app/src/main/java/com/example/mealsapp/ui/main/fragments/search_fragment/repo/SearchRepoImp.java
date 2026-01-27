package com.example.mealsapp.ui.main.fragments.search_fragment.repo;

import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;
import retrofit2.Callback;

public class SearchRepoImp implements SearchRepo {

    @Override
    public void searchByCategory(String category, Callback<MealsResponse> callback) {
        RetrofitClient.getApi().getMealsByCategory(category).enqueue(callback);
    }

    @Override
    public void searchByCountry(String country, Callback<MealsResponse> callback) {
        RetrofitClient.getApi().filterByArea(country).enqueue(callback);
    }

    @Override
    public void searchByIngredient(String ingredient, Callback<MealsResponse> callback) {
        RetrofitClient.getApi().filterByIngredient(ingredient).enqueue(callback);
    }
}

