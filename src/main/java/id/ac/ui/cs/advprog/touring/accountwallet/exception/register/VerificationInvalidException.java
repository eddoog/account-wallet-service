package id.ac.ui.cs.advprog.touring.accountwallet.exception.register;

public class VerificationInvalidException extends RuntimeException {
    public VerificationInvalidException() {
        super("The link is either wrong or expired");
    }

}
