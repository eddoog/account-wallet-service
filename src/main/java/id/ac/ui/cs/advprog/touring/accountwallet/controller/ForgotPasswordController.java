package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.CheckOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ProvideOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.service.ForgotPasswordService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/auth/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/provide-otp")
    public ResponseEntity<ForgotPasswordResponse> provideOTP (
            @RequestBody ProvideOTPRequest request
    ) throws MessagingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        return ResponseEntity.ok(forgotPasswordService.provideOTP(request));
    }

    @PostMapping("/check-otp")
    public ResponseEntity<ForgotPasswordResponse> checkOTP (
            @RequestBody CheckOTPRequest request
    ) {
        return ResponseEntity.ok(forgotPasswordService.checkOTP(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ForgotPasswordResponse> changePassword (
            @RequestBody ForgotPasswordRequest request
    ) {
        return ResponseEntity.ok(forgotPasswordService.changePassword(request));
    }
}
