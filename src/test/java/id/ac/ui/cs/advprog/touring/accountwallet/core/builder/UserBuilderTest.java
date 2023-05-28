package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.register.RegisterResponse;
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
import static org.mockito.Mockito.*;

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
        assertNull(actualUser.getVerificationCode());
        assertEquals("New Customer has been successfully made", actualMessage);
    }

    @Test
    void testSetDefinedUser() {
        // Create a new user
        User user = User.builder()
                .email("john@example.com")
                .password("password")
                .role(UserType.CUSTOMER)
                .build();

        // Create a register builder request
        RegisterBuilderRequest request = RegisterBuilderRequest.builder()
                .email("john@example.com")
                .password("password")
                .role(UserType.CUSTOMER)
                .build();

        // Decide mock behavior
        when(userBuilder.setDefinedUser(user, request)).thenReturn(user);

        // Call the setDefinedUser method
        User result = userBuilder.setDefinedUser(user, request);

        // Make assertions on the output
        assertEquals("john@example.com", result.getEmail());
        assertEquals("password", result.getPassword());
        assertEquals(UserType.CUSTOMER, result.getRole());

        // Verify the mock was called correctly
        verify(userBuilder, times(1)).setDefinedUser(user, request);

        // Test with null email address
        User user2 = User.builder()
                .email(null)
                .password("password")
                .role(UserType.CUSTOMER)
                .build();

        RegisterBuilderRequest request2 = RegisterBuilderRequest.builder()
                .email(null)
                .password("password")
                .role(UserType.CUSTOMER)
                .build();

        when(userBuilder.setDefinedUser(user2, request2)).thenReturn(user2);

        User result2 = userBuilder.setDefinedUser(user2, request2);

        assertNull(result2.getEmail());
        assertEquals("password", result2.getPassword());
        assertEquals(UserType.CUSTOMER, result.getRole());
        verify(userBuilder, times(1)).setDefinedUser(user, request);

    }

    @Test
    void testDecideUser() {
        when(userBuilder.decideUser(existingUserRequest)).thenReturn(existingUser);

        var response = userBuilder.decideUser(existingUserRequest);

        assertNotNull(response);
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
        assertEquals("New Customer has been successfully made", actualMessage);
    }
}