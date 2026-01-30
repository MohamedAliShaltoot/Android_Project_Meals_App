package com.example.mealsapp.data.favorites;

import io.reactivex.rxjava3.core.Completable;

public class SyncFavoritesUseCaseImpl implements SyncFavoritesUseCase {
    private final FavoritesSyncManager manager;

    public SyncFavoritesUseCaseImpl(FavoritesSyncManager manager) {
        this.manager = manager;
    }

    @Override
    public Completable execute() {
        return manager.syncFromFirestore();
    }
}
