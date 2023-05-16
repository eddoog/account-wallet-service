package id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile;

public class UsernameAlreadyUsedException extends RuntimeException {
    public UsernameAlreadyUsedException() {
        super("Username is already used by another.");
    }
}
