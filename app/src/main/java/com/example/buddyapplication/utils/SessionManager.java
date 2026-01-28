package com.example.buddyapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREF_NAME = "BuddySession";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_USERNAME = "username";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String username) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        //TODO: Clear preferences and redirect to LoginActivity
        editor.clear();
        editor.commit();
    }
}
