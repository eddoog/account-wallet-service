package id.ac.ui.cs.advprog.touring.accountwallet.exception;

public class UserHasBeenVerifiedException extends RuntimeException {
    public UserHasBeenVerifiedException() {
        super("Sorry, this account has already been verified");
    }

}
