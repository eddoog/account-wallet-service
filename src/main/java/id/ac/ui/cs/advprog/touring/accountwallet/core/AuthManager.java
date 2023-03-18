package id.ac.ui.cs.advprog.touring.accountwallet.core;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.Tool;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;

public class AuthManager {
    static AuthManager instance;

    private AuthManager() { }

    public static AuthManager getInstance() {
        if (instance == null) instance = new AuthManager();
        return instance;
    }

    public Boolean validatePassword(User user, String password) {
        Tool passwordHasher = new PasswordHasher(password);
        String hashedPassword = passwordHasher.execute();
        
        return user.getPassword() == hashedPassword;
    }

    public String generateToken(User user) {
        Tool tokenGenerator = new TokenGenerator();
        String token = tokenGenerator.execute();
        
        return token;
    }
}
