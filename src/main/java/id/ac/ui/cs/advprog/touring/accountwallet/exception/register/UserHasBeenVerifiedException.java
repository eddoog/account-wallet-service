package id.ac.ui.cs.advprog.touring.accountwallet.exception.register;

public class UserHasBeenVerifiedException extends RuntimeException {
    public UserHasBeenVerifiedException() {
        super("Sorry, this account has already been verified");
    }

}
