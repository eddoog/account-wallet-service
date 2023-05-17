package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.register.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NonVerifiedUserBuilderTest {
    private RegisterBuilderRequest request;
    private User newUser;
    private RegisterResponse expectedResponse;
    @Spy
    private NonVerifiedUserBuilder nonVerifiedUserBuilder;
    @BeforeEach
    void setUp() {
        request = RegisterBuilderRequest.builder()
                .email("test@test.com")
                .password("testPassword")
                .username("testUsername")
                .role(UserType.CUSTOMER)
                .verificationCode(null)
                .build();

        newUser = User.builder()
                .email("test@test.com")
                .password("testPassword")
                .username("testUsername")
                .verificationCode(null)
                .isEnabled(true)
                .role(UserType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        expectedResponse = RegisterResponse.builder()
                .user(newUser)
                .message("New Customer has been successfully made")
                .build();
    }

    @Test
    void createUserShouldCreateNewTourGuide() {
        var actualResponse = nonVerifiedUserBuilder.createUser(request, newUser);

        assertEquals(expectedResponse.getUser(), actualResponse.getUser());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }
}