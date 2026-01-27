package com.example.mealsapp.ui.auth.register.presenter;

public interface RegisterPresenter {

    void register(String name, String email, String password);

    void onDestroy();
}

