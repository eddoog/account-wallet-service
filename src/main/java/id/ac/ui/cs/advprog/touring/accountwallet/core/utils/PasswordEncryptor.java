package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncryptor implements AuthTool {
    public PasswordEncryptor(String password) {
        this.password = password;
    }
    private String password;

    @Override
    public String execute() {
        Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        String encryptedPassword = argon.hash(10, 16384, 1, password);

        return encryptedPassword;
    }
}
