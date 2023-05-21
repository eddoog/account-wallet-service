package id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet;

public class ApprovalRejectedException extends RuntimeException {
    public ApprovalRejectedException() {
        super("Approval rejected by admin");
    }
}
