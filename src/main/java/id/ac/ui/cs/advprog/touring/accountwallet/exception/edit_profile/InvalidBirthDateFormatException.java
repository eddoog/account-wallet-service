package id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile;

public class InvalidBirthDateFormatException extends RuntimeException {
    public InvalidBirthDateFormatException() {
        super("Please enter a valid birth date format to proceed with the edit.");
    }
}
