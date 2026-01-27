package com.example.mealsapp.ui.main.fragments.search_fragment.repo;

import com.example.mealsapp.data.model.MealsResponse;
import retrofit2.Callback;

public interface SearchRepo {

    void searchByCategory(String category, Callback<MealsResponse> callback);

    void searchByCountry(String country, Callback<MealsResponse> callback);

    void searchByIngredient(String ingredient, Callback<MealsResponse> callback);
}

