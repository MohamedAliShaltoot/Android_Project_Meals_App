package com.example.mealsapp.data.profile;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
//
//public class ProfileRepositoryImpl implements ProfileRepo {
//
//    private final FirebaseAuth auth;
//    private final FirebaseFirestore db;
//
//    public ProfileRepositoryImpl() {
//        auth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//    }
//
//    @Override
//    public FirebaseUser getCurrentUser() {
//        return auth.getCurrentUser();
//    }
//
//    @Override
//    public void getUserName(String uid, OnUserNameCallback callback) {
//        db.collection("users")
//                .document(uid)
//                .get()
//                .addOnSuccessListener(doc -> {
//                    if (doc.exists() && doc.getString("name") != null) {
//                        callback.onSuccess(doc.getString("name"));
//                    } else {
//                        callback.onFailure();
//                    }
//                })
//                .addOnFailureListener(e -> callback.onFailure());
//    }
//
//    @Override
//    public void logout(OnLogoutCallback callback) {
//        auth.signOut();
//
//        GoogleSignInClient googleSignInClient =
//                GoogleSignIn.getClient(
//                        com.google.firebase.FirebaseApp.getInstance().getApplicationContext(),
//                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                .requestEmail()
//                                .build()
//                );
//
//        googleSignInClient.signOut().addOnCompleteListener(task -> callback.onComplete());
//    }
//}

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