package com.example.mealsapp.data.favorites;

import com.example.mealsapp.data.database.dao.FavoriteMeal;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface FavoritesRepository {

    Flowable<List<FavoriteMeal>> getAllFavorites();
    Completable removeFavorite(FavoriteMeal meal);


}

