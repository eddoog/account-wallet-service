package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.EmailTool;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.register.SendVerificationEmail;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.register.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public class VerifiedUserBuilder extends UserBuilder {
    @Override
    protected RegisterResponse createUser(RegisterBuilderRequest request, User user) throws MessagingException, UnsupportedEncodingException {
        user.setVerificationCode(request.getVerificationCode());
        user.setIsEnabled(false);

        EmailTool sendVerificationEmail = new SendVerificationEmail(user, request.getSiteURL(), request.getMailSender());
        sendVerificationEmail.execute();

        return RegisterResponse
                .builder()
                .user(user)
                .message("Please verify your account in your email")
                .build();
    }
}