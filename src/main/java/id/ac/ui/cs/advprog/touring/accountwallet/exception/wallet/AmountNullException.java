package id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet;

public class AmountNullException extends RuntimeException {
    public AmountNullException() {
        super("Amount can't be null");
    }
}
