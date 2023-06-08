package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.AuthManager;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.login.*;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.*;
import id.ac.ui.cs.advprog.touring.accountwallet.model.Session;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.SessionRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UserNotFoundException(email);

        var authManager = AuthManager.getInstance();

        boolean isPasswordValid = authManager.validatePassword(user.get(), password);
        if (!isPasswordValid) throw new InvalidEmailOrPasswordException();

        if (Boolean.FALSE.equals(user.get().getIsEnabled())) {
            throw new UserNotEnabledException(user.get().getEmail());
        }

        String token = authManager.generateToken(user.get());

        var newSession = Session.builder()
                .token(token)
                .user(user.get())
                .build();

        sessionRepository.save(newSession);

        return LoginResponse.builder()
                .token(token)
                .user(user.get())
                .build();
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        String token = request.getToken();

        Optional<Session> session = sessionRepository.findByToken(token);
        if (session.isEmpty()) throw new InvalidTokenException(token);

        sessionRepository.deleteById(session.get().getId());

        return LogoutResponse.builder()
                .message("Logout berhasil")
                .build();
    }

    @Override
    public ValidateResponse validate(ValidateRequest request) {
        String token = request.getToken();

        Optional<Session> session = sessionRepository.findByToken(token);
        if (session.isEmpty()) throw new InvalidTokenException(token);

        return ValidateResponse.builder()
                .user(session.get().getUser())
                .build();
    }
}
