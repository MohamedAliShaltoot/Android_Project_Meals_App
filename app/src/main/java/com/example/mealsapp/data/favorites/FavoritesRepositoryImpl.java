package com.example.mealsapp.data.favorites;


import com.example.mealsapp.data.database.dao.FavoriteMeal;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private final FavoritesLocalDataSource local;
    private final FavoritesRemoteDataSource remote;

    public FavoritesRepositoryImpl(
            FavoritesLocalDataSource local,
            FavoritesRemoteDataSource remote
    ) {
        this.local = local;
        this.remote = remote;
    }

    @Override
    public Flowable<List<FavoriteMeal>> getAllFavorites() {
        return local.getAll();
    }

    @Override
    public Completable removeFavorite(FavoriteMeal meal) {
        return local.delete(meal)
                .andThen(remote.removeFavorite(meal.idMeal));
    }
}