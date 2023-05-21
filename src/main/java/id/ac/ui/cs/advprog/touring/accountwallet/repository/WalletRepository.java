package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTransferRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WalletRepository {
    private final Map<Integer, WalletApprovalRequest> approvalList = new HashMap<>();
    private final List<WalletTransferRequest> topUpHistory = new ArrayList<>();

    public List<WalletTransferRequest> getTopUpHistory() {
        return topUpHistory;
    }

    public Map<Integer, WalletApprovalRequest> getApprovalList() {
        return approvalList;
    }
}
