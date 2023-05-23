package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletHistoryResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTransferRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PutMapping("/topup")
    public ResponseEntity<WalletResponse> topUp(@RequestBody WalletTopUpRequest request) {
        WalletResponse response = walletService.topUp(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/transfer")
    public ResponseEntity<WalletResponse> transfer(@RequestBody WalletTransferRequest request) {
        WalletResponse response = walletService.transfer(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/approval")
    public ResponseEntity<WalletResponse> approval(@RequestBody WalletApprovalRequest request) {
        WalletResponse response = walletService.approval(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<WalletHistoryResponse> history(@RequestParam String email) {
        WalletHistoryResponse response = walletService.history(email);
        return ResponseEntity.ok(response);
    }
}
