package id.ac.ui.cs.advprog.touring.accountwallet.core.wallet;


public class IDRCurrencyConverter implements CurrencyConverter {
    @Override
    public Integer getConvertedCurrency(Integer amount) {
        return amount;
    }
}
