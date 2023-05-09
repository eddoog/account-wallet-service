package id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile;

public class AgeRestrictionException extends RuntimeException {
    public AgeRestrictionException() {
        super("Your age doesn't meet the guideline we have.");
    }
}
