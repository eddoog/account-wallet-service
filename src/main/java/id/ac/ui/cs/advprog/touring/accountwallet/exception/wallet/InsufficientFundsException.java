package id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
