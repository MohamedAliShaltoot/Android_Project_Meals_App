package com.example.mealsapp.ui.main.fragments.profile_fragment.presneter;

import com.example.mealsapp.ui.main.fragments.profile_fragment.repo.ProfileRepo;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePresenterImp implements ProfilePresenter {

    private ProfileView view;
    private ProfileRepo repo;

    public ProfilePresenterImp(ProfileView view, ProfileRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void loadUserData() {
        FirebaseUser user = repo.getCurrentUser();
        if (user == null || view == null) return;

        view.showUserEmail(user.getEmail());

        if (user.getPhotoUrl() != null) {
            view.showUserImage(user.getPhotoUrl().toString());
        } else {
            view.showPlaceholderImage();
        }

        repo.getUserName(user.getUid(), new ProfileRepo.OnUserNameCallback() {
            @Override
            public void onSuccess(String name) {
                if (view != null) {
                    view.showUserName(name);
                }
            }

            @Override
            public void onFailure() {
                if (view != null) {
                    view.showUserName(user.getDisplayName());
                }
            }
        });
    }

    @Override
    public void logout() {
        repo.logout(() -> {
            if (view != null) {
                view.navigateToLogin();
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

