package id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile;

public class UsernameAlreadyUsedException extends RuntimeException {
    public UsernameAlreadyUsedException() {
        super("Username is already used by another");
    }
}
