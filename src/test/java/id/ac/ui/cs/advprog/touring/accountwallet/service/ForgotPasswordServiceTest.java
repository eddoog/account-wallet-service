package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.CheckOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ProvideOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.forgotpassword.InvalidOTPCodeException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.forgotpassword.WrongOTPCodeException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.OneTimePassword;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.OneTimePasswordRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ForgotPasswordServiceTest {
    @InjectMocks
    private ForgotPasswordServiceImpl forgotPasswordService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private OneTimePasswordRepository oneTimePasswordRepository;

    @Mock
    private JavaMailSender javaMailSender;

    private User validUser, anotherValidUser, disabledUser;
    private OneTimePassword validOneTimePassword, anotherOneTimePassword, expiredOneTimePassword;

    @BeforeEach
    void setUp() {
        LocalDateTime createdAt = LocalDateTime.now().minusMinutes(30);

        validUser = User.builder()
                .id(1)
                .username("testUsername")
                .email("test@example.com")
                .password("testPassword")
                .role(UserType.CUSTOMER)
                .isEnabled(true)
                .createdAt(createdAt)
                .build();

        anotherValidUser = User.builder()
                .id(2)
                .username("testUsername")
                .email("test2@example.com")
                .password("testPassword")
                .role(UserType.CUSTOMER)
                .isEnabled(true)
                .createdAt(createdAt)
                .build();

        disabledUser = User.builder()
                .id(3)
                .username("testDisabledUsername")
                .email("testDisabled@example.com")
                .password("testDisabledPassword")
                .role(UserType.TOURGUIDE)
                .isEnabled(false)
                .createdAt(createdAt)
                .build();

        validOneTimePassword = OneTimePassword.builder()
                .id(4)
                .oneTimePasswordCode(123456)
                .user(validUser)
                .createdAt(LocalDateTime.now().minusMinutes(4))
                .build();

        anotherOneTimePassword = OneTimePassword.builder()
                .id(5)
                .oneTimePasswordCode(234567)
                .user(anotherValidUser)
                .createdAt(LocalDateTime.now().minusMinutes(4))
                .build();

        expiredOneTimePassword = OneTimePassword.builder()
                .id(6)
                .oneTimePasswordCode(123456)
                .user(validUser)
                .createdAt(LocalDateTime.now().minusMinutes(20))
                .build();
    }
    @Test
    void whenUsingValidEmailShouldProvideOTP() throws Exception {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(validUser));
        when(oneTimePasswordRepository.findByOneTimePasswordCode(any(Integer.class))).thenReturn(Optional.of(validOneTimePassword));
        doReturn(mock(MimeMessage.class)).when(javaMailSender).createMimeMessage();

        var response = forgotPasswordService.provideOTP(ProvideOTPRequest.builder().email(email).build());

        assertNotNull(response);
        assertEquals("Please check your email to get the OTP code", response.getMessage());
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void whenGivingInvalidEmailShouldThrowUserNotFoundException() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        var request = ProvideOTPRequest.builder().email("nonexistent@example.com").build();

        assertThrows(UserNotFoundException.class, () ->
                forgotPasswordService.provideOTP(request)
        );

        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void whenGivingValidEmailButUserIsDisabledShouldThrowError() {
        String email = "disabled@example.com";

        var optionalUser = Optional.of(disabledUser);
        when(userRepository.findByEmail(email)).thenReturn(optionalUser);

        var request = ProvideOTPRequest.builder().email(email).build();

        assertThrows(UserNotFoundException.class, () -> forgotPasswordService.provideOTP(request));

        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void whenOTPCodeIsValidShouldReturnSuccessResponse() {
        int otpCode = 123456;

        when(oneTimePasswordRepository.findByOneTimePasswordCode(otpCode)).thenReturn(Optional.of(validOneTimePassword));

        var response = forgotPasswordService.checkOTP(CheckOTPRequest.builder().otpCode(otpCode).email("test@example.com").build());

        assertNotNull(response);
        assertEquals("Please fill out the form to change your password", response.getMessage());
    }

    @Test
    void whenOTPCodeDoesNotExistShouldReturnInvalidOTPCodeException() {
        int otpCode = 987654;

        when(oneTimePasswordRepository.findByOneTimePasswordCode(otpCode)).thenReturn(Optional.empty());

        var request = CheckOTPRequest.builder().otpCode(otpCode).email("test@example.com").build();

        assertThrows(InvalidOTPCodeException.class, () -> forgotPasswordService.checkOTP(request));
    }

    @Test
    void whenOTPCodeHasExpiredShouldReturnWrongOTPCodeException() {
        int otpCode = 123456;

        when(oneTimePasswordRepository.findByOneTimePasswordCode(otpCode)).thenReturn(Optional.of(expiredOneTimePassword));

        var request = CheckOTPRequest.builder().otpCode(otpCode).email("test@example.com").build();

        assertThrows(WrongOTPCodeException.class, () -> forgotPasswordService.checkOTP(request));
    }

    @Test
    void whenOTPCodeValidButWrongUserShouldThrowWrongOTPCodeException() {
        int otpCode = 234567;

        when(oneTimePasswordRepository.findByOneTimePasswordCode(otpCode)).thenReturn(Optional.of(anotherOneTimePassword));

        var request = CheckOTPRequest.builder().otpCode(otpCode).email("test@example.com").build();

        assertThrows(WrongOTPCodeException.class, () -> forgotPasswordService.checkOTP(request));
    }

    @Test
    void whenChangePasswordWithValidEmailShouldChangePassword() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(validUser));

        var response = forgotPasswordService.changePassword(ForgotPasswordRequest.builder().email(email).newPassword("newTestPassword").build());

        assertNotNull(response);
        assertEquals("Your password has been changed successfully", response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void whenChangePasswordWithInvalidEmailShouldThrowUserNotFoundException() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        var request = ForgotPasswordRequest.builder().email(email).newPassword("newTestPassword").build();
        assertThrows(UserNotFoundException.class, () -> forgotPasswordService.changePassword(request));

        verify(userRepository, never()).save(any(User.class));
    }
}
