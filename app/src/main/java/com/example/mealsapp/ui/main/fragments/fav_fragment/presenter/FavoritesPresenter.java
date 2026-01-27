package com.example.mealsapp.ui.main.fragments.fav_fragment.presenter;

import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;

public interface FavoritesPresenter {

    void loadFavorites();

    void removeFavorite(FavoriteMeal meal);
}

