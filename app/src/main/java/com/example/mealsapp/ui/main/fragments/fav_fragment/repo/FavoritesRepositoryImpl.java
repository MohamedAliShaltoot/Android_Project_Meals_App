package com.example.mealsapp.ui.main.fragments.fav_fragment.repo;


import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.database.localDatabase.FavoriteMealDao;
import com.example.mealsapp.utils.FirestoreFavoritesRepository;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private final FavoriteMealDao dao;

    public FavoritesRepositoryImpl(FavoriteMealDao dao) {
        this.dao = dao;
    }

    @Override
    public Flowable<List<FavoriteMeal>> getAllFavorites() {
        return dao.getAllFavorites();
    }
    @Override
    public Completable removeFavorite(FavoriteMeal meal) {
        return dao.delete(meal)
                .andThen(
                        new FirestoreFavoritesRepository()
                                .removeFavorite(meal.idMeal)
                );
    }

}
