package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.register.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifiedUserBuilderTest {
    @Mock
    private JavaMailSender javaMailSender;
    private RegisterBuilderRequest request;
    private User newUser;
    private RegisterResponse expectedResponse;
    @Spy
    private VerifiedUserBuilder verifiedUserBuilder;
    @BeforeEach
    void setUp() {
        request = RegisterBuilderRequest.builder()
                .email("test@test.com")
                .password("testPassword")
                .username("testUsername")
                .role(UserType.TOURGUIDE)
                .verificationCode("0123456789")
                .siteURL("testURLSite")
                .mailSender(javaMailSender)
                .build();

        newUser = User.builder()
                .email("test@test.com")
                .password("testPassword")
                .username("testUsername")
                .verificationCode("0123456789")
                .isEnabled(false)
                .role(UserType.TOURGUIDE)
                .createdAt(LocalDateTime.now())
                .build();

        expectedResponse = RegisterResponse.builder()
                .user(newUser)
                .message("Please verify your account in your email")
                .build();
    }

    @Test
    void createUserShouldCreateNewTourGuide() throws MessagingException, UnsupportedEncodingException {
        doReturn(mock(MimeMessage.class)).when(javaMailSender).createMimeMessage();

        var actualResponse = verifiedUserBuilder.createUser(request, newUser);

        assertEquals(expectedResponse.getUser(), actualResponse.getUser());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }
}