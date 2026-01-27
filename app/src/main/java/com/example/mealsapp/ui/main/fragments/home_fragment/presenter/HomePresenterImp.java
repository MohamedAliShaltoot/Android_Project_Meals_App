package com.example.mealsapp.ui.main.fragments.home_fragment;

import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenterImp implements HomeContract.Presenter {

    private HomeContract.View view;
    private HomeRepo repo;

    public HomePresenterImp(HomeContract.View view, HomeRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getCategories() {
        repo.getCategories(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (view != null && response.isSuccessful() && response.body() != null) {
                    view.showCategories(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                if (view != null) {
                    view.showError(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getRandomMeal() {
        repo.getRandomMeal(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (view != null && response.isSuccessful()
                        && response.body() != null
                        && response.body().getMeals() != null) {

                    view.showRandomMeal(response.body().getMeals().get(0));
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                if (view != null) {
                    view.showError(t.getMessage());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

