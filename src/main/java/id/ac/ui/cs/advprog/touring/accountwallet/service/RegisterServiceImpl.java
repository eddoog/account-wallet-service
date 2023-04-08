package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.RegisterManager;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.CustomerBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.TourGuideBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.core.builder.UserBuilder;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.UserDoesExistException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{
    private final UserRepository userRepository;
    private RegisterManager registerManager = RegisterManager.getInstance();
    private UserBuilder userBuilder;
    @Override
    public RegisterResponse register(RegisterRequest request, String URLSite) {
        String email = request.getEmail();
        String password = request.getPassword();
        String username = request.getUsername();
        UserType role = UserType.fromString(request.getRole());

        if (doesUserExist(email)) throw new UserDoesExistException(email);

        String encryptedPassword = registerManager.encryptPassword(password);

        String verificationCode;

        boolean isCustomer = role.toString().equals(UserType.CUSTOMER.toString());

        if (isCustomer) {
            verificationCode = null;
            userBuilder = new CustomerBuilder();
        } else {
            verificationCode = registerManager.generateVerificationCode();
            userBuilder = new TourGuideBuilder();
        }

        RegisterResponse response = userBuilder.buildUser(
                RegisterBuilderRequest.builder()
                .email(email)
                .password(encryptedPassword)
                .username(username)
                .role(role)
                .verificationCode(verificationCode)
                .URLSite(URLSite)
                .build()
        );

        return response;
    }

    @Override
    public RegisterResponse verify(String verificationCode) {
        // TODO: Do verification
        return null;
    }

    private Boolean doesUserExist(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) return false; // Check whether the user has already been registered in the system

        User user = userOptional.get();

        return user.getIsEnabled();
    }
}
