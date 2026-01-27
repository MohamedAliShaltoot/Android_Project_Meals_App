package com.example.mealsapp.ui.auth.login.repo;

import android.content.Intent;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public interface LoginRepo {

    void loginWithEmail(String email, String password, OnLoginCallback callback);

    void getGoogleSignInIntent(OnGoogleSignInIntent callback);

    void loginWithGoogleCredential(AuthCredential credential, OnLoginCallback callback);

    interface OnLoginCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(String message);
    }

    interface OnGoogleSignInIntent {
        void onIntentReady(Intent intent);
    }
}

