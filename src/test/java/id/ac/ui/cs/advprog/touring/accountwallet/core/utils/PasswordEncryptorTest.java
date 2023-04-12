package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncryptorTest {

    private PasswordEncryptor passwordEncryptor;
    private String plainPassword;

    @BeforeEach
    public void setUp() {
        plainPassword = "testPassword";
        passwordEncryptor = new PasswordEncryptor(plainPassword);
    }

    @Test
    public void testExecute() {
        String encryptedPassword = passwordEncryptor.execute();

        assertThat(encryptedPassword).isNotEmpty();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        boolean isValid = argon2.verify(encryptedPassword, plainPassword);

        assertThat(isValid).isTrue();
    }
}
