package id.ac.ui.cs.advprog.touring.accountwallet.core.wallet;


public class USDCurrencyConverter implements CurrencyConverter {
    @Override
    public Integer getConvertedCurrency(Integer amount) {
        return amount * 14500;
    }
}
