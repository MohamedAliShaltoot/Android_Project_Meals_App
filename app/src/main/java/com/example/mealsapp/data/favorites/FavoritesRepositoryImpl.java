package com.example.mealsapp.data.favorites;


import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.database.localDatabase.FavoriteMealDao;
import com.example.mealsapp.utils.FirestoreFavoritesRepository;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

//public class FavoritesRepositoryImpl implements FavoritesRepository {
//
//    private final FavoriteMealDao dao;
//
//    public FavoritesRepositoryImpl(FavoriteMealDao dao) {
//        this.dao = dao;
//    }
//
//    @Override
//    public Flowable<List<FavoriteMeal>> getAllFavorites() {
//        return dao.getAllFavorites();
//    }
//    @Override
//    public Completable removeFavorite(FavoriteMeal meal) {
//        return dao.delete(meal)
//                .andThen(
//                        new FirestoreFavoritesRepository()
//                                .removeFavorite(meal.idMeal)
//                );
//    }
//
//}
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