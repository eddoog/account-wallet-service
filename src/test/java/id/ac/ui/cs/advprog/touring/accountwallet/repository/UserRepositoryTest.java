package id.ac.ui.cs.advprog.touring.accountwallet.repository;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Mock
    private UserRepository mockUserRepository;

    @Test
    void testFindByEmail() {
        // Setup
        String email = "test@test.com";
        User expectedUser = User.builder()
                .email(email)
                .password("testPassword")
                .username("testUsername")
                .verificationCode("0123456789")
                .isEnabled(false)
                .role(UserType.TOURGUIDE)
                .createdAt(LocalDateTime.now())
                .build();

        when(mockUserRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        // Exercise
        var actualUser = mockUserRepository.findByEmail(email);

        // Verify
        assertEquals(expectedUser, actualUser.get());
    }

    @Test
    void testFindByVerificationCode() {
        // Setup
        String verificationCode = "0123456789";
        User expectedUser = User.builder()
                .email("test@test.com")
                .password("testPassword")
                .username("testUsername")
                .verificationCode(verificationCode)
                .isEnabled(false)
                .role(UserType.TOURGUIDE)
                .createdAt(LocalDateTime.now())
                .build();

        // Decide mock behaviour
        when(mockUserRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.of(expectedUser));

        // Find user
        var actualUser = mockUserRepository.findByVerificationCode(verificationCode);

        // Then
        assertEquals(expectedUser, actualUser.get());
    }
}
