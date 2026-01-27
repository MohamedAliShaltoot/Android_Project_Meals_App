package com.example.mealsapp.ui.main.fragments.fav_fragment.presenter;



import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.ui.main.fragments.fav_fragment.repo.FavoritesRepository;

public class FavoritesPresenterImpl implements FavoritesPresenter {

    private final FavoritesView view;
    private final FavoritesRepository repository;

    public FavoritesPresenterImpl(
            FavoritesView view,
            FavoritesRepository repository
    ) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadFavorites() {
        repository.getAllFavorites(meals ->
                view.showFavorites(meals)
        );
    }

    @Override
    public void removeFavorite(FavoriteMeal meal) {
        repository.removeFavorite(meal);
        view.showRemoveMessage(meal.name);
    }
}

