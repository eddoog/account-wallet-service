package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.*;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface RegisterService {
    RegisterResponse register(RegisterRequest request, String URLSite) throws MessagingException, UnsupportedEncodingException;
    RegisterResponse verify(String verificationCode);

}
