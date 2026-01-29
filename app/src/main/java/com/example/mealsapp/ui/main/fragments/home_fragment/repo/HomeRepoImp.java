package com.example.mealsapp.ui.main.fragments.home_fragment.repo;


import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;
import io.reactivex.rxjava3.core.Single;


public class HomeRepoImp implements HomeRepo {


    @Override
    public Single<CategoriesResponse> getCategories() {
        return RetrofitClient.getApi().getCategories();
    }

    @Override
    public Single<MealsResponse> getRandomMeal() {
        return RetrofitClient.getApi().getRandomMeal();
    }
}

