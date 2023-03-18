package id.ac.ui.cs.advprog.touring.accountwallet.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String token) {
        super("Token " + token.substring(0, 15) + "... is not valid");
    }
}
