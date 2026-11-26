package com.example.appdev_debugging.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREFS_NAME = "session_prefs";
    private static final String KEY_EMAIL = "logged_in_email";

    private final SharedPreferences prefs;
    private String currentEmail;

    public SessionManager(Context context) {
        this.prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void createSession(String email, boolean rememberMe) {
        currentEmail = email;

        if (rememberMe) {
            prefs.edit().putString(KEY_EMAIL, email).apply();
        } else {
            prefs.edit().remove(KEY_EMAIL).apply();
        }
    }

    public String getLoggedInEmail() {
        if (currentEmail != null) return currentEmail;

        return prefs.getString(KEY_EMAIL, null);
    }

    public boolean isLoggedIn() {
        return getLoggedInEmail() != null;
    }

    public void logout() {
        currentEmail = null;
        prefs.edit().remove(KEY_EMAIL).apply();
    }
}
