package id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException() {
        super("Amount can't be negative");
    }
}
