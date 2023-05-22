package id.ac.ui.cs.advprog.touring.accountwallet.core.wallet;


public class EuroCurrencyConverter implements CurrencyConverter {
    @Override
    public Integer getConvertedCurrency(Integer amount) {
        return amount * 16000;
    }
}
