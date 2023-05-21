package id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet;

public class IndexNotFoundException extends RuntimeException {
    public IndexNotFoundException() {
        super("Index not found");
    }
}
