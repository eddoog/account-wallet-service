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
        String content = getContent();

        MimeMessage message = getMessage(content);

        mailSender.send(message);
    }

    private String getContent() {
        String verifyURL = siteURL + "/auth/verify?code=" + user.getVerificationCode();
        String content = String.format(
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
        return content;
    }

    private MimeMessage getMessage(String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("adproa17@gmail.com", "A17 Account Wallet");
        helper.setTo(user.getEmail());
        helper.setSubject("Please verify your registration");
        helper.setText(content, true);
        return message;
    }
}
