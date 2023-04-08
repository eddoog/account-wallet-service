package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

public class VerificationEmail implements EmailTool {
    private User user;
    private String siteURL;
    private JavaMailSender mailSender;
    public VerificationEmail(User user, String siteURL) {
        this.user = user;
        this.siteURL = siteURL;
        this.mailSender = new JavaMailSenderImpl();
    }

    @Override
    public void execute() throws MessagingException, UnsupportedEncodingException {
        String content = getContent();

        MimeMessage message = getMessage(content);

        mailSender.send(message);
    }

    private String getContent() {
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        String content = String.format(
                "Dear %s,<br>" +
                "Please click the link below to verify your registration:<br>" +
                "<h4>" +
                    "<a href=\"%s\" target=\"_blank\">Click this to complete the verification process</a>" +
                "</h4>" +
                "Thank you,<br>" +
                "Alfredo.",
                user.getUsername(),
                verifyURL
        );
        return content;
    }

    private MimeMessage getMessage(String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("alfredo31austin@gmail.com", "Alfredo");
        helper.setTo(user.getEmail());
        helper.setSubject("Please verify your registration");
        helper.setText(content, true);
        return message;
    }
}
