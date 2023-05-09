package id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile;

public class InvalidFullNameFormatException extends RuntimeException{
    public InvalidFullNameFormatException() {
        super("Please enter a valid name format to proceed with the edit.");
    }
}
