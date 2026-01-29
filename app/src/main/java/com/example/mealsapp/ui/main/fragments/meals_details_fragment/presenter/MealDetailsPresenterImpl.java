package com.example.mealsapp.ui.main.fragments.meals_details_fragment.presenter;



import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.ui.main.fragments.meals_details_fragment.repo.MealDetailsRepository;
import com.example.mealsapp.utils.SnackType;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl
        implements MealDetailsContract.Presenter {

    private MealDetailsContract.View view;
    private final MealDetailsRepository repo;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private Meal currentMeal;
    private boolean isFavorite;

    public MealDetailsPresenterImpl(
            MealDetailsContract.View view,
            MealDetailsRepository repo
    ) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void loadMeal(String mealId) {
        disposable.add(
                repo.getMealDetails(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response.getMeals() == null ||
                                            response.getMeals().isEmpty()) return;

                                    currentMeal = response.getMeals().get(0);
                                    view.showMeal(currentMeal);

                                    checkFavorite();
                                },
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    private void checkFavorite() {
        disposable.add(
                repo.isFavorite(currentMeal.getIdMeal())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    isFavorite = result;
                                    view.showFavoriteState(result);
                                },
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void toggleFavorite() {
        if (currentMeal == null) return;

        FavoriteMeal fav = new FavoriteMeal(
                currentMeal.getIdMeal(),
                currentMeal.getStrMeal(),
                currentMeal.getStrMealThumb()
        );

        disposable.add(
                (isFavorite
                        ? repo.removeFavorite(fav)
                        : repo.addFavorite(fav))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isFavorite = !isFavorite;
                                    view.showFavoriteState(isFavorite);
                                    view.showMessage(
                                            currentMeal.getStrMeal() +
                                                    (isFavorite
                                                            ? " Added to favorites"
                                                            : " Removed from favorites"),
                                            isFavorite
                                                    ? SnackType.SUCCESS
                                                    : SnackType.INFO
                                    );
                                },
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void clear() {
        disposable.clear();
        view = null;
    }
}

