package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.EmailTool;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.SendVerificationEmail;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public class TourGuideBuilder extends UserBuilder {
    @Override
    protected RegisterResponse createUser(RegisterBuilderRequest request, User user) throws MessagingException, UnsupportedEncodingException {
        user.setVerificationCode(request.getVerificationCode());
        user.setIsEnabled(false);

        EmailTool sendVerificationEmail = new SendVerificationEmail(user, request.getURLSite(), request.getMailSender());
        sendVerificationEmail.execute();

        RegisterResponse response = RegisterResponse
                .builder()
                .user(user)
                .message("Please verify your account in your email")
                .build();

        return response;
    }
}