package id.ac.ui.cs.advprog.touring.accountwallet.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterManagerTest {

    private RegisterManager registerManager;
    @BeforeEach
    void setUp() {
        registerManager = RegisterManager.getInstance();
    }

    @Test
    void testEncryptPassword() {
        String password = "password";
        String encryptedPassword = registerManager.encryptPassword(password);
        Assertions.assertNotNull(encryptedPassword);
        Assertions.assertEquals(60, encryptedPassword.length());
        Assertions.assertTrue(encryptedPassword.matches("\\$2a\\$10\\$.{53}"));
    }

    @Test
    void testGenerateVerificationCode() {
        String code = registerManager.generateVerificationCode();
        Assertions.assertNotNull(code);
        Assertions.assertEquals(10, code.length());
        Assertions.assertTrue(code.matches("[a-zA-Z0-9]{10}"));
    }

    @Test
    void testGetInstance() {
        RegisterManager newInstance = RegisterManager.getInstance();
        assertThat(newInstance).isNotNull().isInstanceOf(RegisterManager.class).isSameAs(registerManager);
    }

    @Test
    void testOnlyOneInstanceCanExistAtTheSameTime() {
        RegisterManager instance1 = RegisterManager.getInstance();
        RegisterManager instance2 = RegisterManager.getInstance();
        Assertions.assertSame(instance1, instance2);
    }

}