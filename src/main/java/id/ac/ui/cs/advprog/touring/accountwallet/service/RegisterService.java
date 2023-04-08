package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.*;

public interface RegisterService {
    RegisterResponse register(RegisterRequest request, String URLSite);
    RegisterResponse verify(String verificationCode);

}
