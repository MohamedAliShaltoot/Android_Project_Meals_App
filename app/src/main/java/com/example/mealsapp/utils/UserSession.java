package com.example.mealsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserSession {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_IS_GUEST = "is_guest";

    //Guest
    public static void loginGuest(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_IS_GUEST, true)
                .apply();
    }
    public static boolean isGuest(Context context) {
        if (context == null) return true; // fail-safe
        SharedPreferences prefs =
                context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        return prefs.getBoolean("is_guest", true);
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

    // User
    public static void saveUser(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_IS_GUEST, false) // IMPORTANT
                .putString("uid", user.getUid())
                .putString("name", user.getDisplayName())
                .apply();
    }

    public static void clear(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
}

