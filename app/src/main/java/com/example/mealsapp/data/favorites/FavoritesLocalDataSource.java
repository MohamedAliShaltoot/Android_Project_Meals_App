package com.example.mealsapp.data.favorites;

import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.database.localDatabase.FavoriteMealDao;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class FavoritesLocalDataSource {
    private final FavoriteMealDao dao;

    public FavoritesLocalDataSource(FavoriteMealDao dao) {
        this.dao = dao;
    }

    public Flowable<List<FavoriteMeal>> getAll() {
        return dao.getAllFavorites();
    }

    public Completable delete(FavoriteMeal meal) {
        return dao.delete(meal);
    }
}
