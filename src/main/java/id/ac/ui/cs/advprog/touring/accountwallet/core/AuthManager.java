package id.ac.ui.cs.advprog.touring.accountwallet.core;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.AuthTool;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.TokenGenerator;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

public class AuthManager {
    private static final AuthManager instance = new AuthManager();

    private AuthManager() { }

    public static AuthManager getInstance() {
        return instance;
    }

    public Boolean validatePassword(User user, String password) {
        Argon2 argon = Argon2Factory.create(Argon2Types.ARGON2id);

        return argon.verify(user.getPassword(), password);
    }

    public String generateToken(User user) {
        AuthTool tokenGenerator = new TokenGenerator(user);
        String token = tokenGenerator.execute();
        
        return token;
    }
}
