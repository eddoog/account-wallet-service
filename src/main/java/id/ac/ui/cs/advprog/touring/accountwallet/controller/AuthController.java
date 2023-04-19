package id.ac.ui.cs.advprog.touring.accountwallet.controller;

<<<<<<< HEAD
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LoginRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LoginResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LogoutRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LogoutResponse;
=======
import id.ac.ui.cs.advprog.touring.accountwallet.dto.*;
import id.ac.ui.cs.advprog.touring.accountwallet.service.AuthService;
>>>>>>> 12274b01d98961abba8d1aa279abca57e90c3a92
import lombok.RequiredArgsConstructor;
import id.ac.ui.cs.advprog.touring.accountwallet.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
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

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponse> validate (
            @RequestBody ValidateRequest request
    ) {
        return ResponseEntity.ok(authService.validate(request));
    }
}
