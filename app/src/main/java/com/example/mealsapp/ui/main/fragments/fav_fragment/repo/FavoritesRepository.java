package com.example.mealsapp.ui.main.fragments.fav_fragment.repo;


import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import java.util.List;

public interface FavoritesRepository {

    void getAllFavorites(FavoritesCallback callback);

    void removeFavorite(FavoriteMeal meal);

    interface FavoritesCallback {
        void onResult(List<FavoriteMeal> meals);
    }
}

