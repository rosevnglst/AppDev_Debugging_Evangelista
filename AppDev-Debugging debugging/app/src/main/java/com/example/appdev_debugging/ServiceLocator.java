package com.example.appdev_debugging;

import android.content.Context;

import com.example.appdev_debugging.auth.AuthManager;
import com.example.appdev_debugging.data.InMemoryUserRepository;
import com.example.appdev_debugging.data.UserRepository;
import com.example.appdev_debugging.session.SessionManager;

public final class ServiceLocator {

    public static final String DEMO_NAME = "Demo User";
    public static final String DEMO_EMAIL = "demo@example.com";
    public static final String DEMO_PASSWORD = "Password123";

    private static AuthManager authManager;
    private static SessionManager sessionManager;

    private ServiceLocator() { }

    public static synchronized AuthManager getAuthManager() {
        if (authManager == null) {
            authManager = new AuthManager(provideRepository());
            // Seed one account so you can log in immediately after install.
            authManager.register(DEMO_NAME, DEMO_EMAIL, DEMO_PASSWORD, DEMO_PASSWORD);
        }
        return authManager;
    }

    public static synchronized SessionManager getSessionManager(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context.getApplicationContext());
        }
        return sessionManager;
    }

    private static UserRepository provideRepository() {
        return new InMemoryUserRepository();
        // Later: return new RoomUserRepository(context);
    }
}


