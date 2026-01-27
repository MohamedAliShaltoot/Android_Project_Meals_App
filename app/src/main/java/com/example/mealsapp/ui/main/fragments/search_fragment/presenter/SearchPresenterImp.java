package com.example.mealsapp.ui.main.fragments.search_fragment.presenter;

import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.ui.main.fragments.search_fragment.repo.SearchRepo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenterImp implements SearchPresenter {

    private SearchView view;
    private SearchRepo repo;

    public SearchPresenterImp(SearchView view, SearchRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void search(String filter, String query) {
        if (query.isEmpty() || filter.isEmpty()) return;

        Callback<MealsResponse> callback = new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (view == null) return;

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getMeals() != null) {

                    view.showResults(response.body().getMeals());
                } else {
                    view.showEmpty();
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                if (view != null) {
                    view.showEmpty();
                }
            }
        };

        switch (filter) {
            case "Category":
                repo.searchByCategory(query, callback);
                break;

            case "Country":
                repo.searchByCountry(query, callback);
                break;

            case "Ingredient":
                repo.searchByIngredient(query, callback);
                break;
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

