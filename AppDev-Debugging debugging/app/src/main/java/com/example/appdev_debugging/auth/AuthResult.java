package com.example.appdev_debugging.auth;

import com.example.appdev_debugging.data.model.User;

public class AuthResult {
    /** Which input field an error belongs to. GENERAL = show as a toast/banner. */
    public enum Field { NONE, FULL_NAME, EMAIL, PASSWORD, CONFIRM_PASSWORD, GENERAL }

    private final boolean success;
    private final User user;
    private final String errorMessage;
    private final Field field;

    private AuthResult(boolean success, User user, String errorMessage, Field field) {
        this.success = success;
        this.user = user;
        this.errorMessage = errorMessage;
        this.field = field;
    }

    public static AuthResult success(User user) {
        return new AuthResult(true, user, null, Field.NONE);
    }

    public static AuthResult failure(Field field, String message) {
        return new AuthResult(false, null, message, field);
    }

    public boolean isSuccess() { return success; }
    public User getUser() { return user; }
    public String getErrorMessage() { return errorMessage; }
    public Field getField() { return field; }
}
