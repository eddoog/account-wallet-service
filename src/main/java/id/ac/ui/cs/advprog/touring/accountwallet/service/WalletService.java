package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;

public interface WalletService {
    WalletResponse topUp(WalletRequest request);
}

