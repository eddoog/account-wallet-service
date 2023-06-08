package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.RegisterManager;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.NonVerifiedUserBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.UserBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.VerifiedUserBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.register.RegisterRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.register.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.register.*;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final RegisterManager registerManager = RegisterManager.getInstance();
    private UserBuilder userBuilder;
    private static final int EXPIRED_LIMIT = 15;

    @Value("${verification-domain}")
    private String siteURL;
    @Autowired
    public RegisterServiceImpl(JavaMailSender mailSender, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @Override
    public synchronized RegisterResponse register(@NonNull RegisterRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getEmail();
        String password = request.getPassword();
        String username = request.getUsername();
        var role = UserType.fromString(request.getRole());

        if (registerManager.checkEmailValid(email)) throw new InvalidEmailException(email);

        if (registerManager.checkTrimValid(password)) throw new TrimmedException("Password");

        if (registerManager.checkPasswordLength(password)) throw new PasswordLimitException();

        if (registerManager.checkTrimValid(username)) throw new TrimmedException("Username");

        var optionalUser = doesUserExist(email);

        if (optionalUser.isPresent() && Boolean.TRUE.equals(optionalUser.get().getIsEnabled())) throw new UserDoesExistException(email);

        String encryptedPassword = registerManager.encryptPassword(password);

        String verificationCode = presetBeforeCreating(role);

        RegisterResponse response = userBuilder.buildUser(
                RegisterBuilderRequest.builder()
                        .email(email)
                        .password(encryptedPassword)
                        .username(username)
                        .role(role)
                        .verificationCode(verificationCode)
                        .siteURL(siteURL)
                        .userIfAlreadyBeenMade(optionalUser)
                        .mailSender(mailSender)
                        .build()
        );

        userRepository.save(response.getUser());

        return response;
    }

    @Override
    public synchronized RegisterResponse verify(String verificationCode) {
        var userOptional = userRepository.findByVerificationCode(verificationCode);

        if (userOptional.isEmpty()) throw new VerificationInvalidException();

        var user = userOptional.get();

        if (Boolean.TRUE.equals(user.getIsEnabled())) throw new UserHasBeenVerifiedException();

        long checkDuration = Duration.between(user.getCreatedAt(), LocalDateTime.now()).toMinutes();

        if (checkDuration > EXPIRED_LIMIT) throw new VerificationInvalidException();

        user.setVerificationCode(null);
        user.setIsEnabled(true);

        userRepository.save(user);

        return RegisterResponse.builder()
                .user(user)
                .message("Your account has been verified")
                .build();
    }

    private Optional<User> doesUserExist(String email) {
        return userRepository.findByEmail(email);
    }



    String presetBeforeCreating(@NonNull UserType role) {
        String verificationCode;

        boolean isCustomer = role.toString().equals(UserType.CUSTOMER.toString());

        if (isCustomer) {
            verificationCode = null;
            userBuilder = new NonVerifiedUserBuilder();
        } else {
            verificationCode = registerManager.generateVerificationCode();
            userBuilder = new VerifiedUserBuilder();
        }

        return verificationCode;
    }
}