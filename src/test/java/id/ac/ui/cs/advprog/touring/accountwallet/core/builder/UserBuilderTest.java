package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBuilderTest {

    @Mock
    private UserBuilder userBuilder;
    private User newUser, existingUser;
    private RegisterBuilderRequest newUserRequest, existingUserRequest;

    @BeforeEach
    void setUp() {
        newUserRequest = RegisterBuilderRequest.builder()
                .username("testUsername")
                .email("test@test.com")
                .password("testPassword")
                .role(UserType.CUSTOMER)
                .build();

        newUser = User.builder()
                .username("testUsername")
                .email("test@test.com")
                .password("testPassword")
                .role(UserType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .verificationCode(null)
                .isEnabled(true)
                .build();

        existingUser = User.builder()
                .username("testUsername")
                .email("test@test.com")
                .password("testPassword")
                .role(UserType.TOURGUIDE)
                .createdAt(LocalDateTime.now())
                .verificationCode("0123456789")
                .isEnabled(false)
                .build();

        existingUserRequest = RegisterBuilderRequest.builder()
                .username("testUsername")
                .email("test@test.com")
                .password("testPassword")
                .role(UserType.CUSTOMER)
                .userIfAlreadyBeenMade(Optional.of(existingUser))
                .build();
    }

    @Test
    void buildUserShouldCreateNewUserWhenUserIsNotPresent() throws MessagingException, UnsupportedEncodingException {
        // Decide expected response
        var expectedResponse = RegisterResponse.builder()
                .user(newUser)
                .message("New Customer has been successfully made")
                .build();

        // Decide mock behaviour
        when(userBuilder.buildUser(newUserRequest)).thenReturn(expectedResponse);

        // When
        var actualResponse = userBuilder.buildUser(newUserRequest);
        var actualUser = actualResponse.getUser();
        var actualMessage = actualResponse.getMessage();

        // Then
        assertNotNull(actualResponse);
        assertEquals("test@test.com", actualUser.getEmail());
        assertEquals("testUsername", actualUser.getUsername());
        assertEquals(UserType.CUSTOMER, actualUser.getRole());
        assertEquals(true, actualUser.getIsEnabled());
        assertEquals(null, actualUser.getVerificationCode());
        assertEquals("New Customer has been successfully made", actualMessage);
    }

    @Test
    void buildUserShouldUpdateExistingUserWhenUserIsPresent() throws MessagingException, UnsupportedEncodingException {
        // Decide expected response
        var expectedResponse = RegisterResponse.builder()
                .user(newUser)
                .message("New Customer has been successfully made")
                .build();

        // Decide mock behaviour
        when(userBuilder.buildUser(existingUserRequest)).thenReturn(expectedResponse);

        // When
        var actualResponse = userBuilder.buildUser(existingUserRequest);
        var actualUser = actualResponse.getUser();
        var actualMessage = actualResponse.getMessage();

        // Then verify
        assertNotNull(actualResponse);
        assertEquals("test@test.com", actualUser.getEmail());
        assertEquals("testUsername", actualUser.getUsername());
        assertNotEquals(existingUser.getRole(), actualUser.getRole());
        assertNotEquals(existingUser.getIsEnabled(), actualUser.getIsEnabled());
        assertNull(actualUser.getVerificationCode());
        assertNotEquals(existingUser.getVerificationCode(), actualUser.getVerificationCode());
        assertEquals("New Customer has been successfully made", actualMessage);
    }
}
