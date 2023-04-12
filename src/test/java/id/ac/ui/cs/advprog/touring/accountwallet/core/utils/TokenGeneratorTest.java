package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenGeneratorTest {

    @InjectMocks
    private TokenGenerator tokenGenerator;

    @Mock
    private User user;

    private Key signInKey;
    private static final String SECRET_KEY = "645367566B59703373367639792F423F4528482B4D6251655468576D5A713474";

    @BeforeEach
    public void setUp() {
        signInKey = tokenGenerator.getSignInKey();
        when(user.getId()).thenReturn(1);
        tokenGenerator = new TokenGenerator(user);
    }

    @Test
    public void testExecute() {
        String token = tokenGenerator.execute();

        assertThat(token).isNotEmpty();

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(signInKey)
                    .build()
                    .parseClaimsJws(token);

            assertThat(claimsJws).isNotNull();
            assertThat(claimsJws.getBody().getSubject()).isEqualTo(user.getId().toString());
        } catch (JwtException e) {
            throw new RuntimeException("Failed to validate the token.");
        }
    }
}
