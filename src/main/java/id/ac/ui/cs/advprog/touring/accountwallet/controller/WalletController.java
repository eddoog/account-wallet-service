package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
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
    public ResponseEntity<WalletResponse> topUp(@RequestBody WalletRequest request) {
        WalletResponse response = walletService.topUp(request);
        return ResponseEntity.ok(response);
    }
}
