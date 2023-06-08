package id.ac.ui.cs.advprog.touring.accountwallet.exception.register;

public class PasswordLimitException extends RuntimeException {
    public PasswordLimitException () {
        super("Password must be at least 5 characters and at most 20 characters");
    }

}
