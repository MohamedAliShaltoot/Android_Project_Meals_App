package com.example.mealsapp.data.favorites;

import android.content.Context;
import com.example.mealsapp.data.database.dao.FavoriteMealDao;
import com.example.mealsapp.data.database.MealsDatabase;
import io.reactivex.rxjava3.core.Completable;

public class FavoritesSyncManager {

    private final FavoriteMealDao dao;
    private final FirestoreFavoritesDataSource firestoreRepo;

    public FavoritesSyncManager(Context context) {
        dao = MealsDatabase.getInstance(context).favoriteMealDao();
        firestoreRepo = new FirestoreFavoritesDataSource();
    }

    public Completable syncFromFirestore() {
        return firestoreRepo.getAllFavorites()
                .flattenAsObservable(list -> list)
                .flatMapCompletable(meal -> dao.insert(meal));
    }
}

