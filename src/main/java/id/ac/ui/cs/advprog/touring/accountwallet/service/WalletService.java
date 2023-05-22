package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTransferRequest;

public interface WalletService {
    WalletResponse topUp(WalletTopUpRequest request);
    WalletResponse transfer(WalletTransferRequest request);

    WalletResponse approval(WalletApprovalRequest request);
}

