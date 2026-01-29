package com.example.mealsapp.ui.main.fragments.search_fragment.presenter;

import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.ui.main.fragments.search_fragment.repo.SearchRepo;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {

    private SearchView view;
    private SearchRepo repo;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public SearchPresenterImp(SearchView view, SearchRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void search(String filter, String query) {
        if (query.isEmpty() || filter.isEmpty()) return;

        switch (filter) {
            case "Category":
                disposable.add(
                        repo.searchByCategory(query)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        this::handleResponse,
                                        throwable -> {
                                            if (view != null) view.showEmpty();
                                        }
                                )
                );
                break;

            case "Country":
                disposable.add(
                        repo.searchByCountry(query)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        this::handleResponse,
                                        throwable -> {
                                            if (view != null) view.showEmpty();
                                        }
                                )
                );
                break;

            case "Ingredient":
                disposable.add(
                        repo.searchByIngredient(query)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        this::handleResponse,
                                        throwable -> {
                                            if (view != null) view.showEmpty();
                                        }
                                )
                );
                break;
        }
    }

    private void handleResponse(MealsResponse response) {
        if (view == null) return;

        List<Meal> meals = response.getMeals();
        if (meals != null && !meals.isEmpty()) {
            view.showResults(meals);
        } else {
            view.showEmpty();
        }
    }

    @Override
    public void onDestroy() {
        disposable.clear();
        view = null;
    }
}
