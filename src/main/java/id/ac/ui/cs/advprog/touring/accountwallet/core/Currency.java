package id.ac.ui.cs.advprog.touring.accountwallet.core;

import java.util.ArrayList;
import java.util.List;

public enum Currency {
    IndonesianRupiah, UnitedStatesDollar, Euro;

    public static List<String> getNames(){
        var ret = new ArrayList<String>();
        for (Currency transportation: Currency.values()) {
            ret.add(transportation.name());
        }
        return ret;
    }
}
