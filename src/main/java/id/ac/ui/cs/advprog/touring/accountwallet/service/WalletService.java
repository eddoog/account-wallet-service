package id.ac.ui.cs.advprog.touring.accountwallet.service;

import java.util.Optional;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletHistoryResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletRefundRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTransferRequest;

public interface WalletService {
    WalletResponse topUp(WalletTopUpRequest request);

    WalletResponse transfer(WalletTransferRequest request);

    WalletHistoryResponse history(Optional<String> email);

    WalletResponse approval(WalletApprovalRequest request);

    WalletResponse refund(WalletRefundRequest request);
}
