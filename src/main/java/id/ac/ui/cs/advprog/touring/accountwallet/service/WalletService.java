package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.WalletRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.WalletResponse;

public interface WalletService {
    WalletResponse topUp(WalletRequest request);
}

