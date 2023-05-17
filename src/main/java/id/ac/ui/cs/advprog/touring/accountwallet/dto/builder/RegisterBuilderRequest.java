package id.ac.ui.cs.advprog.touring.accountwallet.dto.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class RegisterBuilderRequest {
    private String username;
    private String email;
    private String password;
    private UserType role;
    private String verificationCode;
    private String siteURL;
    private Optional<User> userIfAlreadyBeenMade;
    private JavaMailSender mailSender;
}

