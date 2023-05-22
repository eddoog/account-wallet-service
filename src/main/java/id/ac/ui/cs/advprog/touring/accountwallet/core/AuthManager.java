package id.ac.ui.cs.advprog.touring.accountwallet.core;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.AuthTool;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.login.TokenGenerator;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthManager {
    private static final AuthManager instance = new AuthManager();

    private AuthManager() { }

    public static AuthManager getInstance() {
        return instance;
    }

    public Boolean validatePassword(User user, String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, user.getPassword());
    }

    public String generateToken(User user) {
        AuthTool tokenGenerator = new TokenGenerator(user);

        return tokenGenerator.execute();
    }
}
