package id.ac.ui.cs.advprog.touring.accountwallet.core;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;

public class AuthManager {
    static AuthManager instance;

    private AuthManager() { }

    public static AuthManager getInstance() {
        if (instance == null) instance = new AuthManager();
        return instance;
    }

    public Boolean validatePassword(User user, String password) {
        return null;
    }

    public String generateToken(User user) {
        return null;
    }

    public void removeSession(String token) {
    }
}
