package id.ac.ui.cs.advprog.touring.accountwallet.exception;

public class UserDoesExistException extends RuntimeException {
    public UserDoesExistException(String email) {
        super("User with email " + email + " has already been used");
    }
}
