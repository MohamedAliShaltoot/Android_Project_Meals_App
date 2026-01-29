package com.example.mealsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {

    private static final String PREF_NAME = "meals_app_prefs";
    private static final String KEY_FIRST_TIME = "is_first_time";

    public static boolean isFirstTime(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_FIRST_TIME, true);
    }

    public static void setNotFirstTime(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_FIRST_TIME, false).apply();
    }
}

