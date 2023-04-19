package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncryptorTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testExecute() {
        // Arrange
        String password = "testpassword";
        PasswordEncryptor encryptor = new PasswordEncryptor(password);

        // Act
        String encryptedPassword = encryptor.execute();

        // Assert
        assertTrue(passwordEncoder.matches(password, encryptedPassword),
                "The encrypted password does not match the original password");
    }
}
