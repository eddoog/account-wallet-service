package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.RegisterManager;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.EmailTool;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.forgotpassword.SendOTPEmail;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.forgotpassword.OneTimePasswordSupplier;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.CheckOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ProvideOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.forgotpassword.InvalidOTPCodeException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.forgotpassword.WrongOTPCodeException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.OneTimePassword;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.OneTimePasswordRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final UserRepository userRepository;
    private final OneTimePasswordRepository oneTimePasswordRepository;
    private final RegisterManager registerManager = RegisterManager.getInstance();
    private JavaMailSender mailSender;

    @Autowired
    public ForgotPasswordServiceImpl(JavaMailSender mailSender, UserRepository userRepository, OneTimePasswordRepository oneTimePasswordRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.oneTimePasswordRepository = oneTimePasswordRepository;
    }
    @Override
    public ForgotPasswordResponse provideOTP(ProvideOTPRequest request) throws MessagingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        var email = request.getEmail();
        var optionalUser = doesUserExist(email);

        if (optionalUser.isEmpty()) throw new UserNotFoundException(email);

        var user = optionalUser.get();

        if (Boolean.FALSE.equals(user.getIsEnabled())) throw new UserNotFoundException(email);

        oneTimePasswordRepository.deleteByUser(user);

        var newOTPCode = getOTPCode();

        var oneTimePassword = buildOTP(user, newOTPCode);

        oneTimePasswordRepository.save(oneTimePassword);

        sendMail(user, newOTPCode, mailSender);

        return ForgotPasswordResponse.builder().message("Please check your email to get the OTP code").build();
    }

    @Override
    public ForgotPasswordResponse checkOTP(CheckOTPRequest request) {
        Integer otpCode = request.getOtpCode();
        var oneTimePasswordOptional = oneTimePasswordRepository.findByOneTimePasswordCode(otpCode);

        if (oneTimePasswordOptional.isEmpty()) throw new InvalidOTPCodeException(otpCode);

        var oneTimePassword = oneTimePasswordOptional.get();

        long checkDuration = Duration.between(oneTimePassword.getCreatedAt(), LocalDateTime.now()).toMinutes();
        if (checkDuration > 5) throw new WrongOTPCodeException();

        var user = oneTimePassword.getUser();

        if (!user.getEmail().equals(request.getEmail())) throw new WrongOTPCodeException();

        return ForgotPasswordResponse.builder().message("Please fill out the form to change your password").build();
    }

    @Override
    public ForgotPasswordResponse changePassword(ForgotPasswordRequest request) {
        var email = request.getEmail();
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) throw new UserNotFoundException(email);

        var user = userOptional.get();

        user.setPassword(registerManager.encryptPassword(request.getNewPassword()));

        userRepository.save(user);

        return ForgotPasswordResponse.builder().message("Your password has been changed successfully").build();
    }

    private Optional<User> doesUserExist(String email) {
        return userRepository.findByEmail(email);
    }

    private Integer getOTPCode() throws NoSuchAlgorithmException {
        return OneTimePasswordSupplier.createRandomOneTimePassword().get();
    }

    private void sendMail(User user, Integer newOTPCode, JavaMailSender mailSender) throws MessagingException, UnsupportedEncodingException {
        EmailTool sendOTPEmail = new SendOTPEmail(user, newOTPCode, mailSender);
        sendOTPEmail.execute();
    }

    private OneTimePassword buildOTP(User user, Integer otpCode) {
        return OneTimePassword.builder().user(user).oneTimePasswordCode(otpCode).createdAt(LocalDateTime.now()).build();
    }
}