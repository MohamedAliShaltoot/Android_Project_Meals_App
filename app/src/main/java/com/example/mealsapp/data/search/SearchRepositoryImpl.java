package com.example.mealsapp.data.search;

import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;

import io.reactivex.rxjava3.core.Single;


//public class SearchRepositoryImpl implements SearchRepository {
//    @Override
//    public Single<MealsResponse> searchByCategory(String category) {
//        return RetrofitClient.getApi().getMealsByCategory(category);
//    }
//
//    @Override
//    public Single<MealsResponse> searchByCountry(String country) {
//        return RetrofitClient.getApi().filterByArea(country);
//    }
//
//    @Override
//    public Single<MealsResponse> searchByIngredient(String ingredient) {
//        return RetrofitClient.getApi().filterByIngredient(ingredient);
//    }
//    @Override
//    public Single<MealsResponse> searchByName(String name) {
//        return RetrofitClient.getApi().searchByName(name);
//    }
//
//}



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