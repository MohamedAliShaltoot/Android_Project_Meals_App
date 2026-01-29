package com.example.mealsapp.data.network;

import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    @GET("categories.php")
    Single<CategoriesResponse> getCategories();

    @GET("random.php")
    Single<MealsResponse> getRandomMeal();

    @GET("filter.php")
    Single<MealsResponse> getMealsByCategory(@Query("c") String category);

    @GET("lookup.php")
    Single<MealsResponse> getMealDetails(@Query("i") String id);

    @GET("search.php")
    Single<MealsResponse> searchByName(@Query("s") String name);

    @GET("filter.php")
    Single<MealsResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<MealsResponse> filterByArea(@Query("a") String area);
}

