package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WalletRepository {
    private List<WalletApprovalRequest> approvalList = new ArrayList<>();
    private List<WalletTopUpRequest> topUpHistory = new ArrayList<>();

    public List<WalletTopUpRequest> getTopUpHistory() {
        return topUpHistory;
    }

    public List<WalletApprovalRequest> getApprovalList() {
        return approvalList;
    }
}
