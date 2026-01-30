package com.example.mealsapp.data.search;

import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Single;


import com.example.mealsapp.data.meals.MealsRemoteDataSource;


public class SearchRepositoryImpl implements SearchRepository {

    private final MealsRemoteDataSource remote;

    public SearchRepositoryImpl() {
        this.remote = new MealsRemoteDataSource();
    }

    @Override
    public Single<MealsResponse> searchByCategory(String category) {
        return remote.getMealsByCategory(category);
    }

    @Override
    public Single<MealsResponse> searchByCountry(String country) {
        return remote.filterByArea(country);
    }

    @Override
    public Single<MealsResponse> searchByIngredient(String ingredient) {
        return remote.filterByIngredient(ingredient);
    }

    @Override
    public Single<MealsResponse> searchByName(String name) {
        return remote.searchByName(name);
    }
}