package id.ac.ui.cs.advprog.touring.accountwallet.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {
    @Test
    void testGetJavaMailSender() {
        // Setup
        var config = new ApplicationConfig();

        // Test
        var mailSender = config.getJavaMailSender();

        // Verify
        assertNotNull(mailSender);
    }

    @Test
    void testCorsConfigurer() {
        // Setup
        var config = new ApplicationConfig();

        // Test
        WebMvcConfigurer corsConfigurer = config.corsConfigurer();

        // Verify
        assertNotNull(corsConfigurer);
    }
}