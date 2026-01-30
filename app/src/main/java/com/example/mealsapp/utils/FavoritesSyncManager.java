package com.example.mealsapp.utils;

import android.content.Context;

import com.example.mealsapp.data.database.localDatabase.FavoriteMealDao;
import com.example.mealsapp.data.database.localDatabase.MealsDatabase;

import io.reactivex.rxjava3.core.Completable;

public class FavoritesSyncManager {

    private final FavoriteMealDao dao;
    private final FirestoreFavoritesRepository firestoreRepo;

    public FavoritesSyncManager(Context context) {
        dao = MealsDatabase.getInstance(context).favoriteMealDao();
        firestoreRepo = new FirestoreFavoritesRepository();
    }

    public Completable syncFromFirestore() {
        return firestoreRepo.getAllFavorites()
                .flattenAsObservable(list -> list)
                .flatMapCompletable(meal -> dao.insert(meal));
    }
}

