package id.ac.ui.cs.advprog.touring.accountwallet.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("User with email " + email + " does not exist");
    }
}
