package id.ac.ui.cs.advprog.touring.accountwallet.core;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.Tool;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.TokenGenerator;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthManager {
    static AuthManager instance;

    private AuthManager() { }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static AuthManager getInstance() {
        if (instance == null) instance = new AuthManager();
        return instance;
    }

    public Boolean validatePassword(User user, String password) {

        return passwordEncoder.matches(password, user.getPassword());
    }

    public String generateToken(User user) {
        Tool tokenGenerator = new TokenGenerator(user);
        String token = tokenGenerator.execute();
        
        return token;
    }
}
