package com.example.mealsapp.ui.main.fragments.profile_fragment.repo;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileRepoImp implements ProfileRepo {

    private final FirebaseAuth auth;
    private final FirebaseFirestore db;

    public ProfileRepoImp() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    @Override
    public void getUserName(String uid, OnUserNameCallback callback) {
        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists() && doc.getString("name") != null) {
                        callback.onSuccess(doc.getString("name"));
                    } else {
                        callback.onFailure();
                    }
                })
                .addOnFailureListener(e -> callback.onFailure());
    }

    @Override
    public void logout(OnLogoutCallback callback) {
        auth.signOut();

        GoogleSignInClient googleSignInClient =
                GoogleSignIn.getClient(
                        com.google.firebase.FirebaseApp.getInstance().getApplicationContext(),
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build()
                );

        googleSignInClient.signOut().addOnCompleteListener(task -> callback.onComplete());
    }
}

