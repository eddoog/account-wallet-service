package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class PasswordEncryptor implements Tool {
    public PasswordEncryptor(String password) {
        this.password = password;
    }
    private String password;

    @Override
    public String execute() {
        // TODO: Encrypt password
        return null;
    }
}
