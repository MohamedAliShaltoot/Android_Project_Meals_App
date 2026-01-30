package com.example.mealsapp.data.search;

import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Single;


public interface SearchRepository {
    Single<MealsResponse> searchByCategory(String category);
    Single<MealsResponse> searchByCountry(String country);
    Single<MealsResponse> searchByIngredient(String ingredient);
    Single<MealsResponse> searchByName(String name);
}

