package id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile;

public class InvalidNameFormatException extends RuntimeException{
    public InvalidNameFormatException() {
        super("Please enter a valid name format to proceed with the edit.");
    }
}
