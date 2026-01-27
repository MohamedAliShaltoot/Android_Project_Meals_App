package com.example.mealsapp.ui.auth.login.presenter;

public interface LoginView {

    void showLoading();

    void hideLoading();

    void showError(String message);

    void navigateToMain();

    void navigateToRegister();

    void startGoogleSignInIntent(android.content.Intent intent);
}

