package com.example.mealsapp.ui.main.fragments.search_fragment.repo;

import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Single;


public interface SearchRepo {
    Single<MealsResponse> searchByCategory(String category);
    Single<MealsResponse> searchByCountry(String country);
    Single<MealsResponse> searchByIngredient(String ingredient);

}

