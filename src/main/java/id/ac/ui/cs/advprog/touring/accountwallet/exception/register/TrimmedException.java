package id.ac.ui.cs.advprog.touring.accountwallet.exception.register;

public class TrimmedException extends RuntimeException {
    public TrimmedException (String type) {
        super(type + " cannot have leading or trailing spaces");
    }

}
