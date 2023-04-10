package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHasher implements Tool {
    private String password;

    public PasswordHasher(String password) {
        this.password = password;
    }

    @Override
    public String execute() {
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedPassword = bcrypt.encode(password);
        return encryptedPassword;
    }
}
