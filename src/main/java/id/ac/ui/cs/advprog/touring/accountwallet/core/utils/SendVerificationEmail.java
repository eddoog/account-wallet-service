package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

public class SendVerificationEmail implements EmailTool {
    private User user;
    private String siteURL;
    private JavaMailSender mailSender;
    public SendVerificationEmail(User user, String siteURL, JavaMailSender mailSender) {
        this.user = user;
        this.siteURL = siteURL;
        this.mailSender = mailSender;
    }

    @Override
    public void execute() throws MessagingException, UnsupportedEncodingException {
        var content = getContent();

        var message = getMessage(content);

        mailSender.send(message);
    }

    String getContent() {
        var verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        return String.format(
                "Dear %s,<br>" +
                        "Please click the link below to verify your registration:<br>" +
                        "<h4>" +
                        "<a href=\"%s\" target=\"_blank\">Click this to complete the verification process</a>" +
                        "</h4>" +
                        "Thank you,<br>" +
                        "A17 Account Wallet.",
                user.getUsername(),
                verifyURL
        );
    }

    MimeMessage getMessage(String content) throws MessagingException, UnsupportedEncodingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message);
        helper.setFrom("adproa17@gmail.com", "A17 Account Wallet");
        helper.setTo(user.getEmail());
        helper.setSubject("Please verify your registration");
        helper.setText(content, true);
        return message;
    }
}
