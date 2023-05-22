package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.wallet;


import id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet.AmountNegativeException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet.AmountNullException;

public class EuroCurrencyConverter implements CurrencyConverter {
    @Override
    public Integer getConvertedCurrency(Integer amount) {
        if (amount == null) {
            throw new AmountNullException();
        }
        if (amount < 0) {
            throw new AmountNegativeException();
        }
        return amount * 16000;
    }
}
