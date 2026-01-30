package com.example.mealsapp.ui.main.fragments.home_fragment.presenter;
import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.home.HomeRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImp implements HomeContract.Presenter {
    private final HomeContract.View view;
    private final HomeRepository repo;
    private final CompositeDisposable disposable = new CompositeDisposable();


    public HomePresenterImp(HomeContract.View view, HomeRepository repo) {
        this.view = view;
        this.repo = repo;
    }

@Override
public void getCategories() {
    disposable.add(
            repo.getCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::handleCategories,
                            throwable -> view.showError(throwable.getMessage())
                    )
    );
}

    @Override
    public void getRandomMeal() {
        disposable.add(
                repo.getRandomMeal()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::handleRandomMeal,
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }
    private void handleCategories(CategoriesResponse response) {
        if (response != null && response.getCategories() != null) {
            view.showCategories(response.getCategories());
        } else {
            view.showError("No categories found");
        }
    }

    private void handleRandomMeal(MealsResponse response) {
        if (response != null && response.getMeals() != null && !response.getMeals().isEmpty()) {
            view.showRandomMeal(response.getMeals().get(0));
        } else {
            view.showError("No random meal found");
        }
    }

@Override
public void onDestroy() {
    disposable.clear();
}
}

