package com.example.mealsapp.ui.main.fragments.profile_fragment.repo;

import com.google.firebase.auth.FirebaseUser;

public interface ProfileRepo {

    FirebaseUser getCurrentUser();

    void getUserName(
            String uid,
            OnUserNameCallback callback
    );

    void logout(OnLogoutCallback callback);

    interface OnUserNameCallback {
        void onSuccess(String name);
        void onFailure();
    }

    interface OnLogoutCallback {
        void onComplete();
    }
}

