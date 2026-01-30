package com.example.mealsapp.data.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileRemoteDataSource {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public void getUserName(String uid, OnResult<String> callback) {
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

    public void logout(Runnable onComplete) {
        auth.signOut();

        GoogleSignInClient googleSignInClient =
                GoogleSignIn.getClient(
                        com.google.firebase.FirebaseApp
                                .getInstance()
                                .getApplicationContext(),
                        new GoogleSignInOptions.Builder(
                                GoogleSignInOptions.DEFAULT_SIGN_IN
                        )
                                .requestEmail()
                                .build()
                );

        // to force app to choose account
        googleSignInClient.revokeAccess()
                .addOnCompleteListener(task -> onComplete.run());
    }

    public interface OnResult<T> {
        void onSuccess(T data);
        void onFailure();
    }
}