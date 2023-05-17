package id.ac.ui.cs.advprog.touring.accountwallet.exception.forgotpassword;

public class WrongOTPCodeException extends RuntimeException {
    public WrongOTPCodeException() {
        super("THe OTP Code is either wrong or expired");
    }
}