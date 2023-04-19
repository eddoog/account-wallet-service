package id.ac.ui.cs.advprog.touring.accountwallet.core;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.PasswordEncryptor;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.TokenGenerator;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthManagerTest {

    @InjectMocks
    private AuthManager authManager;

    @Mock
    private User user;

    private String plainPassword;
    private String hashedPassword;

    @BeforeEach
    public void setUp() {
        plainPassword = "testPassword";
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor(plainPassword);
        hashedPassword = passwordEncryptor.execute();


        user = User.builder()
                .id(1)
                .email("testEmail@example.com")
                .password(hashedPassword)
                .username("testUsername")
                .role(UserType.ADMIN)
                .verificationCode("testVerificationCode")
                .isEnabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        authManager = AuthManager.getInstance();
    }

    @Test
    public void testGetInstance() {
        AuthManager newInstance = AuthManager.getInstance();
        assertThat(newInstance).isNotNull();
        assertThat(newInstance).isInstanceOf(AuthManager.class);
        assertThat(newInstance).isSameAs(authManager);
    }

    @Test
    public void testValidatePassword() {
        boolean isValid = authManager.validatePassword(user, plainPassword);
        assertThat(isValid).isTrue();
    }

    @Test
    public void testGenerateToken() {
        TokenGenerator tokenGenerator = new TokenGenerator(user);
        String generatedToken = tokenGenerator.execute();
        String token = authManager.generateToken(user);

        assertThat(token).isNotEmpty();

        // Verify the payload
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(tokenGenerator.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertThat(Integer.parseInt(claims.getSubject())).isEqualTo(user.getId());
    }
}
