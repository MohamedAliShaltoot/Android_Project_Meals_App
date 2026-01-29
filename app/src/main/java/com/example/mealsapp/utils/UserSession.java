package com.example.mealsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserSession {

    private static final String PREF_NAME = "user_session";

    public static void saveUser(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        prefs.edit()
                .putString("uid", user.getUid())
                .putString("name", user.getDisplayName())
                .apply();
    }

    public static String getUserName(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return null;

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String savedUid = prefs.getString("uid", null);

        // If different user → ignore old name
        if (!user.getUid().equals(savedUid)) {
            saveUser(context);
        }

        return prefs.getString("name", "User");
    }
}

