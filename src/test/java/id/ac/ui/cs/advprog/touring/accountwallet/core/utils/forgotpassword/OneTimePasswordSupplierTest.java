package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.forgotpassword;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.function.Supplier;

class OneTimePasswordSupplierTest {
    @Test
    void testCreateRandomOneTimePassword() throws NoSuchAlgorithmException {
        Supplier<Integer> otpSupplier = OneTimePasswordSupplier.createRandomOneTimePassword();
        Integer otp = otpSupplier.get();

        Assertions.assertNotNull(otp);
        Assertions.assertEquals(6, String.valueOf(otp).length());
    }
}