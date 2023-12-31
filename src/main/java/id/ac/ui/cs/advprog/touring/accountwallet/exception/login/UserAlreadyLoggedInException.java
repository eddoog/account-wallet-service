package id.ac.ui.cs.advprog.touring.accountwallet.exception.login;

public class UserAlreadyLoggedInException extends RuntimeException {
    public UserAlreadyLoggedInException(String email) {
        super("User with email " + email + " has already logged in");
    }
}
