package com.example.mealsapp.ui.main.fragments.fav_fragment;

import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import java.util.List;

public interface FavoritesView {

    void showFavorites(List<FavoriteMeal> meals);

    void showRemoveMessage(String mealName);
}

