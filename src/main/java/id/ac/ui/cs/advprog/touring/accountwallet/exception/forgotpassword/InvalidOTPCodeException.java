package id.ac.ui.cs.advprog.touring.accountwallet.exception.forgotpassword;

public class InvalidOTPCodeException extends RuntimeException {
    public InvalidOTPCodeException(Integer otpCode) {
        super("OTP Code " + Integer.toString(otpCode) + " is not valid");
    }
}
