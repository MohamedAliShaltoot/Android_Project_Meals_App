package com.example.mealsapp.data.favorites;

import io.reactivex.rxjava3.core.Completable;

public interface SyncFavoritesUseCase {
    Completable execute();
}
