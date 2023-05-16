package id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile;

public class UsernameEmptyInputException extends RuntimeException{
    public UsernameEmptyInputException() {
        super("Username input can't be empty.");
    }
}
