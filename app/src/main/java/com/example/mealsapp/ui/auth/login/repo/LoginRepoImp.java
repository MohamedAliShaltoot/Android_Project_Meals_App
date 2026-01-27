package com.example.mealsapp.ui.auth.login.repo;

import android.content.Context;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRepoImp implements LoginRepo {

    private final FirebaseAuth auth;
    private final Context context;
    private final GoogleSignInClient googleSignInClient;

    public LoginRepoImp(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(com.example.mealsapp.R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    @Override
    public void loginWithEmail(String email, String password, OnLoginCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> callback.onSuccess(auth.getCurrentUser()))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void getGoogleSignInIntent(OnGoogleSignInIntent callback) {
        callback.onIntentReady(googleSignInClient.getSignInIntent());
    }

    @Override
    public void loginWithGoogleCredential(AuthCredential credential, OnLoginCallback callback) {
        auth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> callback.onSuccess(auth.getCurrentUser()))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }
}

