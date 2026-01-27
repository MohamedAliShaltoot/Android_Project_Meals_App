package com.example.mealsapp.ui.auth.register.repo;

import com.google.firebase.auth.FirebaseUser;

public interface RegisterRepo {

    void registerUser(String name, String email, String password, OnRegisterCallback callback);

    interface OnRegisterCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(String message);
    }
}

