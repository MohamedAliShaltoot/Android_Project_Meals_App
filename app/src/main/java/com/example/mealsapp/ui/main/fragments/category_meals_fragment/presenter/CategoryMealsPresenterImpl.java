package com.example.mealsapp.ui.main.fragments.category_meals_fragment.presenter;

import com.example.mealsapp.data.meals.CategoryMealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryMealsPresenterImpl
        implements CategoryMealsContract.Presenter {

    private CategoryMealsContract.View view;
    private final CategoryMealsRepository repo;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public CategoryMealsPresenterImpl(
            CategoryMealsContract.View view,
            CategoryMealsRepository repo
    ) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void loadMeals(String category) {
        disposable.add(
                repo.getMealsByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (view != null && response.getMeals() != null) {
                                        view.showMeals(response.getMeals());
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }

    @Override
    public void clear() {
        disposable.clear();
        view = null;
    }
}

