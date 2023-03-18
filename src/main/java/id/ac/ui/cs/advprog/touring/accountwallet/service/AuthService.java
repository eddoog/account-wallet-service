package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.LoginRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LoginResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LogoutRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.LogoutResponse;

public interface AuthService {
    public LoginResponse login(LoginRequest request);
    public LogoutResponse logout(LogoutRequest request);
}
