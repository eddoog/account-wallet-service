package id.ac.ui.cs.advprog.touring.accountwallet.exception;

public class AgeRestrictionException extends RuntimeException {
    public AgeRestrictionException() {
        super("Username is already used by another");
    }
}
