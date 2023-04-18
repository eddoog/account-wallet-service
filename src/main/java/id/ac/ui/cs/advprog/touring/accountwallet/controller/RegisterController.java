package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.service.RegisterService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;
    @Value("${verification-domain}")
    private String siteURL;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register (
            @RequestBody RegisterRequest request
    ) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.status(201).body(registerService.register(request, getSiteURL()));
    }

    @GetMapping("/verify")
    public ResponseEntity<RegisterResponse> verifyUser(@Param("code") String code) {
        return ResponseEntity.status(201).body(registerService.verify(code));
    }

    private String getSiteURL() {
        return siteURL + "/auth";
    }
}
