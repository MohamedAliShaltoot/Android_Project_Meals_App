package com.example.mealsapp.ui.auth.login.presenter;

public interface LoginPresenter {

    void loginWithEmail(String email, String password);

    void loginWithGoogle();

    void handleGoogleSignInResult(android.content.Intent data);

    void goToRegister();

    void onDestroy();
}

