package id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile;

public class InvalidPhoneNumFormatException extends RuntimeException {
    public InvalidPhoneNumFormatException() {
        super("Please enter a valid phone number format to proceed with the edit");
    }
}
