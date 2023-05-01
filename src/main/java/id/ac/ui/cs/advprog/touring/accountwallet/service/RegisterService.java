package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.*;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface RegisterService {
    RegisterResponse register(RegisterRequest request) throws MessagingException, UnsupportedEncodingException;
    RegisterResponse verify(String verificationCode);

}
