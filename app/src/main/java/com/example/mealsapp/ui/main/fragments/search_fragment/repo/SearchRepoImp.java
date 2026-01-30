package com.example.mealsapp.ui.main.fragments.search_fragment.repo;

import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;
import io.reactivex.rxjava3.core.Single;


public class SearchRepoImp implements SearchRepo {
    @Override
    public Single<MealsResponse> searchByCategory(String category) {
        return RetrofitClient.getApi().getMealsByCategory(category);
    }

    @Override
    public Single<MealsResponse> searchByCountry(String country) {
        return RetrofitClient.getApi().filterByArea(country);
    }

    @Override
    public Single<MealsResponse> searchByIngredient(String ingredient) {
        return RetrofitClient.getApi().filterByIngredient(ingredient);
    }
    @Override
    public Single<MealsResponse> searchByName(String name) {
        return RetrofitClient.getApi().searchByName(name);
    }

}

