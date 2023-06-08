package id.ac.ui.cs.advprog.touring.accountwallet.exception.login;

public class InvalidEmailOrPasswordException extends RuntimeException {
    public InvalidEmailOrPasswordException() {
        super("Email or password is invalid");
    }
}
