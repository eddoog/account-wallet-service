package id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet;

import java.util.List;

import id.ac.ui.cs.advprog.touring.accountwallet.model.TopUpApproval;
import id.ac.ui.cs.advprog.touring.accountwallet.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletHistoryResponse {
    List<TopUpApproval> pendingApprovals;
    List<Transaction> transactions;
}
