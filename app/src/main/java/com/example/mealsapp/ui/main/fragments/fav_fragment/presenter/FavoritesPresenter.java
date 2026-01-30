package com.example.mealsapp.ui.main.fragments.fav_fragment.presenter;

import com.example.mealsapp.data.database.dao.FavoriteMeal;

public interface FavoritesPresenter {

    void loadFavorites();

    void removeFavorite(FavoriteMeal meal);
}

