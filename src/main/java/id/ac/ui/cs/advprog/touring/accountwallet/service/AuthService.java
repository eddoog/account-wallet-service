package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.login.*;

public interface AuthService {
    public LoginResponse login(LoginRequest request);
    public LogoutResponse logout(LogoutRequest request);
    public ValidateResponse validate(ValidateRequest request);
}
