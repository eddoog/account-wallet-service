package id.ac.ui.cs.advprog.touring.accountwallet.model;

import java.util.ArrayList;
import java.util.List;

public enum Currency {
    INDONESIANRUPIAH, UNITEDSTATESDOLLAR, EURO;

    public static List<String> getNames(){
        var ret = new ArrayList<String>();
        for (Currency transportation: Currency.values()) {
            ret.add(transportation.name());
        }
        return ret;
    }

    @Override
    public String toString() {
        switch(this) {
            case INDONESIANRUPIAH: return "Indonesian Rupiah";
            case UNITEDSTATESDOLLAR: return "United States Dollar";
            case EURO: return "Euro";
            default: throw new IllegalArgumentException();
        }
    }

    public static String fromString(String currencyType) {
        for (Currency b : Currency.values()) {
            if (b.toString().equalsIgnoreCase(currencyType)) {
                return b.toString();
            }
        }
        throw new IllegalArgumentException("No currency type " + currencyType + " found");
    }
}
