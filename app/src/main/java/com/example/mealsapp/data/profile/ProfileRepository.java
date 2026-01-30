package com.example.mealsapp.data.profile;

import com.google.firebase.auth.FirebaseUser;
import io.reactivex.rxjava3.core.Completable;

public interface ProfileRepository {

    FirebaseUser getCurrentUser();

    void getUserName(String uid, ProfileRemoteDataSource.OnResult<String> callback);

    Completable logout();
}
