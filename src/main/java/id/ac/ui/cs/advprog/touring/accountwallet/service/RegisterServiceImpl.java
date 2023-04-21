package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.RegisterManager;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.CustomerBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.TourGuideBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.UserBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.UserDoesExistException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.UserHasBeenVerifiedException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.VerificationInvalidException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import jakarta.mail.MessagingException;
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
    @Autowired
    private JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final RegisterManager registerManager = RegisterManager.getInstance();
    private UserBuilder userBuilder;

    @Value("${verification-domain}")
    private String siteURL;

    @Override
    public RegisterResponse register(RegisterRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getEmail();
        String password = request.getPassword();
        String username = request.getUsername();
        var  role = UserType.fromString(request.getRole());

        var optionalUser = doesUserExist(email);
        var getIsEnabled = (boolean) optionalUser.get().getIsEnabled();

        if (optionalUser.isPresent() && getIsEnabled) throw new UserDoesExistException(email);

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
    public RegisterResponse verify(String verificationCode) {
        var userOptional = userRepository.findByVerificationCode(verificationCode);

        if (userOptional.isEmpty()) throw new VerificationInvalidException();

        var user = userOptional.get();

        var getIsEnabled = (boolean) user.getIsEnabled();

        if (getIsEnabled) throw new UserHasBeenVerifiedException();

        long checkDuration = Duration.between(user.getCreatedAt(), LocalDateTime.now()).toMinutes();

        if (checkDuration > 15) throw new VerificationInvalidException();

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

    private String presetBeforeCreating(UserType role) {
        String verificationCode;

        boolean isCustomer = role.toString().equals(UserType.CUSTOMER.toString());

        if (isCustomer) {
            verificationCode = null;
            userBuilder = new CustomerBuilder();
        } else {
            verificationCode = registerManager.generateVerificationCode();
            userBuilder = new TourGuideBuilder();
        }

        return verificationCode;
    }
}
