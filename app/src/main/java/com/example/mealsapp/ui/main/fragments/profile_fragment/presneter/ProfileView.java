package com.example.mealsapp.ui.main.fragments.profile_fragment.presneter;

public interface ProfileView {

    void showUserName(String name);

    void showUserEmail(String email);

    void showUserImage(String imageUrl);

    void showPlaceholderImage();

    void navigateToLogin();
}

