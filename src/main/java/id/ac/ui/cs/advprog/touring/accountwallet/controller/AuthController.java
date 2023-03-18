package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.LoginRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LoginResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LogoutRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LogoutResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout (
            @RequestBody LogoutRequest request
    ) {
        return ResponseEntity.ok(authService.logout(request));
    }
}
