package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncryptor implements AuthTool {
    public PasswordEncryptor(String password) {
        this.password = password;
    }
    private String password;

    @Override
    public String execute() {
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();

        return bcrypt.encode(password);
    }
}
