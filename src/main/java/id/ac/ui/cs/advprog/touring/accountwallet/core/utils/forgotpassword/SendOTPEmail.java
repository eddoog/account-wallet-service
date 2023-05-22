package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.forgotpassword;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.EmailTool;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

public class SendOTPEmail implements EmailTool {
    private User user;
    private Integer otpCode;
    private JavaMailSender mailSender;
    public SendOTPEmail(User user, Integer otpCode, JavaMailSender mailSender) {
        this.user = user;
        this.otpCode = otpCode;
        this.mailSender = mailSender;
    }

    @Override
    public void execute() throws MessagingException, UnsupportedEncodingException {
        var content = getContent();

        var message = getMessage(content);

        mailSender.send(message);
    }

    public String getContent() {
        return String.format(
                "Dear %s,<br>" +
                        "Please use this OTP Code to reset your password:<br>" +
                        "<h4>" +
                        "<h2>%d</h2>" +
                        "</h4>" +
                        "Thank you,<br>" +
                        "A17 Account Wallet.",
                user.getUsername(),
                otpCode
        );
    }

    public MimeMessage getMessage(String content) throws MessagingException, UnsupportedEncodingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message);
        helper.setFrom("adproa17@gmail.com", "A17 Account Wallet");
        helper.setTo(user.getEmail());
        helper.setSubject("This is your OTP Code");
        helper.setText(content, true);
        return message;
    }
}