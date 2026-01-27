package com.example.mealsapp.ui.auth.register.presenter;

public interface RegisterView {

    void showLoading();

    void hideLoading();

    void showError(String message);

    void enableRegisterButton();

    void disableRegisterButton();

    void navigateToMain();
}

