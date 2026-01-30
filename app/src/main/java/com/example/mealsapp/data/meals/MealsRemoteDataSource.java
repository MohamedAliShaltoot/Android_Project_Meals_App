package com.example.mealsapp.data.meals;

import com.example.mealsapp.data.model.CategoriesResponse;
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
    public Single<MealsResponse> getMealsByCategory(String category) {
        return api.getMealsByCategory(category);
    }
    public Single<CategoriesResponse> getCategories() {
        return api.getCategories();
    }

    public Single<MealsResponse> getRandomMeal() {
        return api.getRandomMeal();
    }
    public Single<MealsResponse> filterByArea(String area) {
        return api.filterByArea(area);
    }

    public Single<MealsResponse> filterByIngredient(String ingredient) {
        return api.filterByIngredient(ingredient);
    }

    public Single<MealsResponse> searchByName(String name) {
        return api.searchByName(name);
    }
}
