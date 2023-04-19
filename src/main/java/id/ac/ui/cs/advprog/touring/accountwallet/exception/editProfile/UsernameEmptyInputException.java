package id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile;

public class UsernameEmptyInputException extends RuntimeException{
    public UsernameEmptyInputException() {
        super("Username input can't be empty.");
    }
}
