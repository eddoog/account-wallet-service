package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class SendVerificationEmailTest {
    @Mock
    private User mockUser;
    @Mock
    private JavaMailSender mockMailSender;
    @InjectMocks
    private SendVerificationEmail sendVerificationEmail;

    @BeforeEach
    void setUp() {
        // Set up mock user object
        when(mockUser.getUsername()).thenReturn("Alfredo");
        when(mockUser.getVerificationCode()).thenReturn("0123456789");

        String siteURL = "http://localhost:3000/auth";

        // Initialize SendVerificationEmail instance with mock objects
        sendVerificationEmail = new SendVerificationEmail(mockUser, siteURL, mockMailSender);
    }

    @Test
    void testExecute() throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mock(MimeMessage.class);

        when(mockMailSender.createMimeMessage()).thenReturn(message);

        when(mockUser.getEmail()).thenReturn("alfredo.austin@ui.ac.id");

        sendVerificationEmail.execute();

        verify(mockMailSender).send(message);
    }

    @Test
    void testGetContent() {
        String content = sendVerificationEmail.getContent();

        // Verify that the content contains the correct information
        String expectedContent = "Dear Alfredo,<br>" +
                "Please click the link below to verify your registration:<br>" +
                "<h4>" +
                "<a href=\"http://localhost:3000/auth/verify?code=0123456789\" target=\"_blank\">Click this to complete the verification process</a>" +
                "</h4>" +
                "Thank you,<br>" +
                "A17 Account Wallet.";

        Assertions.assertEquals(content, expectedContent);
    }

    @Test
    void testGetMessage() throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(mockUser.getEmail()).thenReturn("alfredo.austin@ui.ac.id");

        String content = sendVerificationEmail.getContent();

        when(mockMailSender.createMimeMessage()).thenReturn(mimeMessage);

        MimeMessage message = sendVerificationEmail.getMessage(content);

        when(message.getSubject()).thenReturn("Please verify your registration");
        when(message.getFrom()).thenReturn(new Address[] { new InternetAddress("A17 Account Wallet <adproa17@gmail.com>") });
        when(message.getRecipients(MimeMessage.RecipientType.TO)).thenReturn(new Address[] { new InternetAddress("alfredo.austin@ui.ac.id") });

        // Verify that the message has the correct properties
        Assertions.assertEquals("Please verify your registration", message.getSubject());
        Assertions.assertEquals("A17 Account Wallet <adproa17@gmail.com>", message.getFrom()[0].toString() );
        Assertions.assertEquals( "alfredo.austin@ui.ac.id", message.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
    }
}
