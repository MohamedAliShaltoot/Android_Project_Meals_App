package com.example.mealsapp.ui.main.fragments.fav_fragment.repo;

import androidx.lifecycle.LifecycleOwner;
import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.database.localDatabase.FavoriteMealDao;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private final FavoriteMealDao dao;
    private final LifecycleOwner owner;

    public FavoritesRepositoryImpl(
            FavoriteMealDao dao,
            LifecycleOwner owner
    ) {
        this.dao = dao;
        this.owner = owner;
    }

    @Override
    public void getAllFavorites(FavoritesCallback callback) {
        dao.getAllFavorites().observe(owner, callback::onResult);
    }

    @Override
    public void removeFavorite(FavoriteMeal meal) {
        dao.delete(meal);
    }
}

