package id.ac.ui.cs.advprog.touring.accountwallet.exception.login;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String password) {
        super("Password " + "*".repeat(password.length() - 3) + password.substring(password.length() - 3));
    }
}
