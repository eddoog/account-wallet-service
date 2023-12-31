package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.register.RegisterRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.register.*;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegisterServiceTest {
    @InjectMocks
    private RegisterServiceImpl service;
    @Mock
    private UserRepository mockUserRepository;
    private User uniqueUser, userToBeVerified;
    private RegisterRequest uniqueRequest;
    @BeforeEach
    void setUp() {
        LocalDateTime createdAt = LocalDateTime.now().minusMinutes(10);

        uniqueRequest = RegisterRequest.builder()
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password("password")
                .role("Customer")
                .build();

        uniqueUser = User.builder()
                .id(1)
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password("password")
                .role(UserType.CUSTOMER)
                .verificationCode(null)
                .isEnabled(true)
                .createdAt(createdAt)
                .build();

        userToBeVerified = User.builder()
                .id(1)
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password("password")
                .role(UserType.TOURGUIDE)
                .verificationCode("0123456789")
                .isEnabled(false)
                .createdAt(createdAt)
                .build();
    }

    @Test
    void whenRegisterNewUserShouldBeFoundInRepository() throws MessagingException, UnsupportedEncodingException {
        // Set behaviour of saving user in which the id is set to 1
        when(mockUserRepository.save(any(User.class))).thenAnswer(invocation -> {
            var user = invocation.getArgument(0, User.class);
            user.setId(1);
            return user;
        });

        // Save the unique user
        var result = service.register(uniqueRequest).getUser();

        // Checks if register() in RegisterServiceImpl actually attempted saving
        verify(mockUserRepository, atLeastOnce()).save(any(User.class));

        Assertions.assertEquals(uniqueUser.getId(), result.getId());
        Assertions.assertEquals(uniqueUser.getEmail(), result.getEmail());
        Assertions.assertEquals(uniqueUser.getUsername(), result.getUsername());
        Assertions.assertEquals(uniqueUser.getRole(), result.getRole());
    }

    @Test
    void whenRegisterNonVerifiedUserShouldNotGenerateVerificationCode() {
        UserType role = UserType.CUSTOMER;

        var res = service.presetBeforeCreating(role);

        Assertions.assertNull(res);
    }
    @Test
    void whenRegisterVerifiedUserShouldGenerateVerificationCode() {
        UserType role = UserType.TOURGUIDE;

        var res = service.presetBeforeCreating(role);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(10, res.length());
    }

    @Test
    void whenRegisterWithInvalidEmailShouldThrowsError() {
        var invalidRequest = RegisterRequest.builder()
                .username("uniqueuser")
                .email("abcde@examplecom")
                .password("password")
                .role("Customer")
                .build();

        Assertions.assertThrows(InvalidEmailException.class, () -> service.register(invalidRequest));
    }

    @Test
    void whenRegisterWithInvalidPasswordFormatShouldThrowsError() {
        var invalidRequest = RegisterRequest.builder()
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password(" password ")
                .role("Customer")
                .build();

        Assertions.assertThrows(TrimmedException.class, () -> service.register(invalidRequest));
    }

    @Test
    void whenRegisterWithInvalidPasswordLengthShouldThrowsError() {
        var invalidRequest = RegisterRequest.builder()
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password("pass")
                .role("Customer")
                .build();

        Assertions.assertThrows(PasswordLimitException.class, () -> service.register(invalidRequest));
    }

    @Test
    void whenRegisterWithInvalidUsernameFormatShouldThrowsError() {
        var invalidRequest = RegisterRequest.builder()
                .username(" uniqueuser ")
                .email("uniqueuser@example.com")
                .password("password")
                .role("Customer")
                .build();

        Assertions.assertThrows(TrimmedException.class, () -> service.register(invalidRequest));
    }
    @Test
    void whenRegisterWithExistingEmailShouldThrowsError() throws MessagingException, UnsupportedEncodingException {
        // Set behaviour of saving user in which the id is set to 1
        when(mockUserRepository.save(any(User.class))).thenAnswer(invocation -> {
            var user = invocation.getArgument(0, User.class);
            user.setId(1);
            return user;
        });

        // Save the unique user
        service.register(uniqueRequest);

        // Mock the behavior of the userRepository findByEmail method to return the unique user
        when(mockUserRepository.findByEmail(uniqueRequest.getEmail())).thenReturn(Optional.of(uniqueUser));

        // Attempt to register a user with the same email, should throw UserDoesExistException
        var duplicateUser = RegisterRequest.builder()
                .username("duplicateuser")
                .email(uniqueUser.getEmail())
                .password("password")
                .role("Customer")
                .build();

        Assertions.assertThrows(UserDoesExistException.class, () -> service.register(duplicateUser));
    }

    @Test
    void whenRegisterWithExistingEmailButNotEnabledShouldReturnResponse() throws MessagingException, UnsupportedEncodingException {
        // Set behaviour of saving user in which the id is set to 1
        when(mockUserRepository.save(any(User.class))).thenAnswer(invocation -> {
            var user = invocation.getArgument(0, User.class);
            user.setId(1);
            return user;
        });

        var notEnabledUser = User.builder().username("uniqueuser")
                .email("uniqueuser@example.com")
                .isEnabled(Boolean.FALSE)
                .build();

        var notEnabledRequest = RegisterRequest.builder()
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password("uniquepassword")
                .role("Customer")
                .build();

        when(mockUserRepository.findByEmail(notEnabledRequest.getEmail())).thenReturn(Optional.of(notEnabledUser));

        var expectedResponse = service.register(notEnabledRequest);

        Assertions.assertNotNull(expectedResponse);
    }

    @Test
    void whenVerifyUserHaveToBeEnabled() {
        String verificationCode = "0123456789";

        when(mockUserRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.of(userToBeVerified));
        when(mockUserRepository.save(any(User.class))).thenReturn(userToBeVerified);

        var verifyRes = service.verify(verificationCode);

        var verifiedUser = verifyRes.getUser();

        Assertions.assertEquals(userToBeVerified, verifiedUser);
        Assertions.assertTrue(verifiedUser.getIsEnabled());
        Assertions.assertNull(verifiedUser.getVerificationCode());
    }

    @Test
    void whenVerifyWIthIncorrectVerificationCodeShouldThrowsError() {
        String verificationCode = "0123456788";

        when(mockUserRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.empty());

        Assertions.assertThrows(VerificationInvalidException.class, () -> service.verify(verificationCode));
    }

    @Test
    void whenVerifyCodeExpiredShouldThrowError() {
        String verificationCode = "0123456789";

        userToBeVerified.setCreatedAt(LocalDateTime.now().minusMinutes(20));

        when(mockUserRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.of(userToBeVerified));

        Assertions.assertThrows(VerificationInvalidException.class, () -> service.verify(verificationCode));
    }

    @Test
    void whenVerifyWhenUserHasAlreadyBeenEnabledShouldThrowError() {
        String verificationCode = "0123456789";

        userToBeVerified.setIsEnabled(true);

        when(mockUserRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.of(userToBeVerified));

        Assertions.assertThrows(UserHasBeenVerifiedException.class, () -> service.verify(verificationCode));
    }
}