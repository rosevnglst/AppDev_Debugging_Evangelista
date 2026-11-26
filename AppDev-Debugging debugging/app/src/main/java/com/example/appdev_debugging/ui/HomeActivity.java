package com.example.appdev_debugging.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdev_debugging.R;
import com.example.appdev_debugging.ServiceLocator;
import com.example.appdev_debugging.data.model.User;
import com.example.appdev_debugging.session.SessionManager;

public class HomeActivity extends AppCompatActivity{
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = ServiceLocator.getSessionManager(this);

        // Guard: if there's no valid session, bounce back to login.
        String email = sessionManager.getLoggedInEmail();
        User user = email == null ? null : ServiceLocator.getAuthManager().getUser(email);
        if (user == null) {
            sessionManager.logout();
            goToLogin();
            return;
        }

        setContentView(R.layout.activity_home);

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        TextView tvEmail = findViewById(R.id.tvEmail);
        Button btnLogout = findViewById(R.id.btnLogout);

        tvWelcome.setText(getString(R.string    .home_welcome, user.getFullName()));
        tvEmail.setText(getString(R.string.home_email, user.getEmail()));

        // TODO(DEBUG #5): "Log out" sometimes doesn't really log the user out.
        //  Steps to reproduce (fix DEBUG #1 first):
        //    1. Log in WITHOUT ticking "Remember me". Tap "Log out" -> you see the Login screen. Correct.
        //    2. Log in WITH "Remember me" ticked. Tap "Log out" -> Login screen appears. Looks fine...
        //    3. Now swipe the app away from Recents (or Logcat "Terminate App") and open it again.
        //       You land on Home, still logged in, even though you logged out.
        //  Your tasks:
        //    a) Explain why the bug only appears when "Remember me" was ticked AND the app is restarted.
        //       (Think: where does the session live in each case: memory or disk?)
        //    b) Use Android Studio's Device Explorer to open
        //       /data/data/com.example.simplelogin/shared_prefs/ and inspect the XML file there
        //       before login, after login, and after logout. What do you see?
        //    c) Find and fix the root cause. Do NOT fix it by adding extra code here in HomeActivity;
        //       fix the class that is actually responsible for the session.
        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            goToLogin();
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

