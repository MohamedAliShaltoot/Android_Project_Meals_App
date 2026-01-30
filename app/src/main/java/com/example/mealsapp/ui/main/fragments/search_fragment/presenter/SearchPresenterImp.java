package com.example.mealsapp.ui.main.fragments.search_fragment.presenter;

import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.search.SearchRepository;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {

    private SearchView view;
    private SearchRepository repo;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public SearchPresenterImp(SearchView view, SearchRepository repo) {
        this.view = view;
        this.repo = repo;
    }
    @Override
    public void search(String filter, String query) {
        if (query.isEmpty() || filter.isEmpty()) return;

        Single<MealsResponse> source;

        switch (filter) {
            case "Category":
                source = repo.searchByCategory(query);
                break;

            case "Country":
                source = repo.searchByCountry(query);
                break;

            case "Ingredient":
                source = repo.searchByIngredient(query);
                break;

            case "Name":
                source = repo.searchByName(query);
                break;

            default:
                return;
        }

        disposable.add(
                source.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::handleResponse,
                                throwable -> {
                                    if (view != null) view.showEmpty();
                                }
                        )
        );
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
