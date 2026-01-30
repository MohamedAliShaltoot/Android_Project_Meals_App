package com.example.mealsapp.data.profile;


import com.example.mealsapp.data.database.dao.FavoriteMealDao;
import io.reactivex.rxjava3.core.Completable;

public class ProfileLocalDataSource {

    private final FavoriteMealDao dao;

    public ProfileLocalDataSource(FavoriteMealDao dao) {
        this.dao = dao;
    }

    public Completable clearFavorites() {
        return dao.clearAll();
    }
}
