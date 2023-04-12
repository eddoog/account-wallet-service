package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.AuthManager;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.*;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.InvalidTokenException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.UserAlreadyLoggedInException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.WrongPasswordException;
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

        Optional<Session> session = sessionRepository.findByUser(user.get());
        if (session.isPresent()) throw new UserAlreadyLoggedInException(email);

        AuthManager authManager = AuthManager.getInstance();

        Boolean isPasswordValid = authManager.validatePassword(user.get(), password);
        if (!isPasswordValid) throw new WrongPasswordException(password);

        String token = authManager.generateToken(user.get());

        Session.builder()
                .token(token)
                .user(user.get())
                .build();

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

        sessionRepository.deleteByToken(token);

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
