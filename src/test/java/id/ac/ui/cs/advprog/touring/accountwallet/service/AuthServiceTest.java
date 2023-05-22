package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.login.LoginRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.login.LogoutRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.login.ValidateRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.InvalidTokenException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.WrongPasswordException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.Session;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.SessionRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthServiceTest {
    @InjectMocks
    private AuthServiceImpl service;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private SessionRepository mockSessionRepository;

    private User existingUser;

    @BeforeEach
    void setUp() {
        existingUser = User.builder()
                .id(1)
                .username("existinguser")
                .email("existinguser@example.com")
                .password(new BCryptPasswordEncoder().encode("password"))
                .build();
    }

    @Test
    void whenLoginWithExistingUserShouldReturnTokenAndUser() {
        String email = "existinguser@example.com";
        String password = "password";
        String token = "token";

        var request = LoginRequest.builder().email(email).password(password).build();
        var session = Session.builder()
                .token(token)
                .user(existingUser)
                .build();

        when(mockUserRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));
        when(mockSessionRepository.save(any(Session.class))).thenReturn(session);

        var response = service.login(request);

        assertNotNull(response.getToken());
        assertEquals(existingUser, response.getUser());

        verify(mockUserRepository, times(1)).findByEmail(email);
        verify(mockSessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    void whenLoginWithNonExistingUserShouldThrowUserNotFoundException() {
        String email = "nonexistinguser@example.com";
        String password = "password";

        LoginRequest request = new LoginRequest(email, password);

        when(mockUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.login(request));

        verify(mockUserRepository, times(1)).findByEmail(email);
        verify(mockSessionRepository, never()).save(any(Session.class));
    }

    @Test
    void whenLoginWithWrongPasswordShouldThrowWrongPasswordException() {
        String email = "existinguser@example.com";
        String password = "wrongpassword";

        LoginRequest request = new LoginRequest(email, password);

        when(mockUserRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        assertThrows(WrongPasswordException.class, () -> service.login(request));

        verify(mockUserRepository, times(1)).findByEmail(email);
        verify(mockSessionRepository, never()).save(any(Session.class));
    }

    @Test
    void whenLogoutWithValidTokenShouldDeleteSession() {
        String token = "validtoken";

        var request = LogoutRequest.builder().token(token).build();

        when(mockSessionRepository.findByToken(token)).thenReturn(Optional.of(Session.builder().build()));

        var response = service.logout(request);

        assertEquals("Logout berhasil", response.getMessage());

        verify(mockSessionRepository, times(1)).findByToken(token);
        verify(mockSessionRepository, times(1)).deleteByToken(token);
    }

    @Test
    void whenLogoutWithInvalidTokenShouldThrowInvalidTokenException() {
        String token = "invalidtokenbang";

        LogoutRequest request = new LogoutRequest(token);

        when(mockSessionRepository.findByToken(token)).thenReturn(Optional.empty());

        assertThrows(InvalidTokenException.class, () -> service.logout(request));

        verify(mockSessionRepository, times(1)).findByToken(token);
        verify(mockSessionRepository, never()).deleteByToken(token);
    }

    @Test
    void whenValidateWithValidTokenShouldReturnUser() {
        String token = "validtoken";

        var request = ValidateRequest.builder().token(token).build();

        when(mockSessionRepository.findByToken(token)).thenReturn(Optional.of(Session.builder().user(existingUser).build()));

        var response = service.validate(request);

        assertEquals(existingUser, response.getUser());

        verify(mockSessionRepository, times(1)).findByToken(token);
    }

    @Test
    void whenValidateWithInvalidTokenShouldThrowInvalidTokenException() {
        String token = "invalidtokenbang";

        ValidateRequest request = new ValidateRequest(token);

        when(mockSessionRepository.findByToken(token)).thenReturn(Optional.empty());

        assertThrows(InvalidTokenException.class, () -> service.validate(request));

        verify(mockSessionRepository, times(1)).findByToken(token);
    }
}
