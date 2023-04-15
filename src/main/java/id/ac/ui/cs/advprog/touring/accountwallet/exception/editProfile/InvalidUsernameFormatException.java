package id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile;

public class InvalidUsernameFormatException extends RuntimeException {
    public InvalidUsernameFormatException() {
        super("Please enter a valid username format to proceed with the edit");
    }
}
