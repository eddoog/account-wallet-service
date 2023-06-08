package id.ac.ui.cs.advprog.touring.accountwallet.exception.register;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super(email + " is not a valid email");
    }
}
