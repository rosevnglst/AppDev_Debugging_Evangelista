package com.example.appdev_debugging.auth;

import com.example.appdev_debugging.data.UserRepository;
import com.example.appdev_debugging.data.model.User;
import com.example.appdev_debugging.util.PasswordHasher;
import com.example.appdev_debugging.util.Validator;

public class AuthManager {

    static final String INVALID_CREDENTIALS = "Invalid email or password";
    static final String EMAIL_TAKEN = "An account with this email already exists";

    private final UserRepository repository;

    public AuthManager(UserRepository repository) {
        this.repository = repository;
    }

    public AuthResult login(String email, String password) {
        String emailError = Validator.validateEmail(email);

        if (emailError != null) {
            return AuthResult.failure(AuthResult.Field.EMAIL, emailError);
        }

        String passwordError = Validator.validateLoginPassword(password);

        if (passwordError != null) {
            return AuthResult.failure(AuthResult.Field.PASSWORD, passwordError);
        }

        User user = repository.findByEmail(email);

        if (user == null || !PasswordHasher.verify(
                password, user.getSalt(), user.getPasswordHash())) {
            return AuthResult.failure(
                    AuthResult.Field.GENERAL, INVALID_CREDENTIALS);
        }

        return AuthResult.success(user);
    }

    public AuthResult register(String fullName, String email,
                               String password, String confirmPassword) {

        String error;

        if ((error = Validator.validateFullName(fullName)) != null)
            return AuthResult.failure(AuthResult.Field.FULL_NAME, error);

        if ((error = Validator.validateEmail(email)) != null)
            return AuthResult.failure(AuthResult.Field.EMAIL, error);

        if ((error = Validator.validateNewPassword(password)) != null)
            return AuthResult.failure(AuthResult.Field.PASSWORD, error);

        if ((error = Validator.validatePasswordConfirmation(
                password, confirmPassword)) != null)
            return AuthResult.failure(
                    AuthResult.Field.CONFIRM_PASSWORD, error);

        if (repository.exists(email))
            return AuthResult.failure(AuthResult.Field.EMAIL, EMAIL_TAKEN);

        String salt = PasswordHasher.generateSalt();
        String hash = PasswordHasher.hash(password, salt);

        User user = new User(fullName.trim(), email.trim(), hash, salt);

        if (!repository.add(user)) {
            return AuthResult.failure(AuthResult.Field.EMAIL, EMAIL_TAKEN);
        }

        return AuthResult.success(user);
    }

    public User getUser(String email) {
        return repository.findByEmail(email);
    }
}
