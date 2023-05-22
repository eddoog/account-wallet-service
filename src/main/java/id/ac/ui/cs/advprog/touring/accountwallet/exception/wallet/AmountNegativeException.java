package id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException() {
        super("Amount can't be negative");
    }
}
