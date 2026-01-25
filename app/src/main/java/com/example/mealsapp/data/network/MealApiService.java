package com.example.mealsapp.data.network;

import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    @GET("categories.php")
    Call<CategoriesResponse> getCategories();

    @GET("random.php")
    Call<MealsResponse> getRandomMeal();

    @GET("filter.php")
    Call<MealsResponse> getMealsByCategory(@Query("c") String category);
    @GET("lookup.php")
    Call<MealsResponse> getMealDetails(@Query("i") String id);
    @GET("search.php")
    Call<MealsResponse> searchByName(@Query("s") String name);

    @GET("filter.php")
    Call<MealsResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealsResponse> filterByArea(@Query("a") String area);


}

