package com.example.mealsapp.data.favorites;

import com.example.mealsapp.data.database.dao.FavoriteMeal;
import com.example.mealsapp.data.database.dao.FavoriteMealDao;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

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
    public Single<Boolean> isFavorite(String mealId) {
        return dao.isFavorite(mealId);
    }

    public Completable insert(FavoriteMeal meal) {
        return dao.insert(meal);
    }
}
