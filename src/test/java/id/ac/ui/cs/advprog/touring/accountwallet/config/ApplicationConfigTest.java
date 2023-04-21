package id.ac.ui.cs.advprog.touring.accountwallet.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @Mock
    private CorsRegistry registry;

    private WebMvcConfigurer configurer;

    @Test
    void testGetJavaMailSender() {
        // Setup
        var config = new ApplicationConfig();
        var mailSender = config.getJavaMailSender();

        // Verify

        assertNotNull(mailSender);

        assertTrue(mailSender instanceof JavaMailSenderImpl);

        var mailSenderImpl = (JavaMailSenderImpl) mailSender;

        // Test

        assertEquals("smtp.gmail.com", mailSenderImpl.getHost());
        assertEquals(587, mailSenderImpl.getPort());
        assertEquals("adproa17@gmail.com", mailSenderImpl.getUsername());
        assertEquals("wondhriyliqvlcjp", mailSenderImpl.getPassword());

        assertEquals("smtp", mailSenderImpl.getJavaMailProperties().get("mail.transport.protocol"));
        assertEquals("true", mailSenderImpl.getJavaMailProperties().get("mail.smtp.auth"));
        assertEquals("true", mailSenderImpl.getJavaMailProperties().get("mail.smtp.starttls.enable"));
        assertEquals("true", mailSenderImpl.getJavaMailProperties().get("mail.debug"));
    }

    @Test
    void testCorsConfigurer() throws Exception {
        // Setup
        var config = new ApplicationConfig();

        var configurer = config.corsConfigurer();

        configurer.addCorsMappings(registry);
        verify(registry).addMapping("/**");
    }
}