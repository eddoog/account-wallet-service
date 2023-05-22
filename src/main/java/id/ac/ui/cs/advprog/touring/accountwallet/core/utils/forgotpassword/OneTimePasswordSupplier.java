package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.forgotpassword;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Supplier;

public class OneTimePasswordSupplier {
    private static final Integer LENGTH = 6;

    private OneTimePasswordSupplier () {}

    public static Supplier<Integer> createRandomOneTimePassword() throws NoSuchAlgorithmException {
        Random rand = SecureRandom.getInstanceStrong();

        return () -> {
            var oneTimePassword = new StringBuilder();
            for (var i = 0; i < LENGTH; i++) {
                var randomNumber = rand.nextInt(10);
                oneTimePassword.append(randomNumber);
            }
            return Integer.parseInt(oneTimePassword.toString().trim());
        };
    }
}