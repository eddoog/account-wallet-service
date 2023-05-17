package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.CheckOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ProvideOTPRequest;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface ForgotPasswordService {
    ForgotPasswordResponse provideOTP(ProvideOTPRequest request) throws MessagingException, UnsupportedEncodingException, NoSuchAlgorithmException;
    ForgotPasswordResponse checkOTP(CheckOTPRequest request);
    ForgotPasswordResponse changePassword(ForgotPasswordRequest request);
}