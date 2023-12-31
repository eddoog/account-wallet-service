package id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet;

public class CurrencyNotSupportedException extends RuntimeException {
    public CurrencyNotSupportedException(String currencyType) {
        super("Currency type " + currencyType + " is not supported");
    }
}
