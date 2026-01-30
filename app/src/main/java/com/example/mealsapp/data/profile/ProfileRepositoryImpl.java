package com.example.mealsapp.data.profile;


import com.google.firebase.auth.FirebaseUser;
import io.reactivex.rxjava3.core.Completable;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileRemoteDataSource remote;
    private final ProfileLocalDataSource local;

    public ProfileRepositoryImpl(
            ProfileRemoteDataSource remote,
            ProfileLocalDataSource local
    ) {
        this.remote = remote;
        this.local = local;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return remote.getCurrentUser();
    }

    @Override
    public void getUserName(String uid, ProfileRemoteDataSource.OnResult<String> callback) {
        remote.getUserName(uid, callback);
    }

    @Override
    public Completable logout() {
        return local.clearFavorites()
                .andThen(
                        Completable.fromAction(() ->
                                remote.logout(() -> {})
                        )
                );
    }
}