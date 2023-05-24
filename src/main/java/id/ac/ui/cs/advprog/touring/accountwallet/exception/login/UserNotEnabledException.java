package id.ac.ui.cs.advprog.touring.accountwallet.exception.login;

public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException(String email) {
        super(String.format("User %s not enabled yet", email));
    }
}
