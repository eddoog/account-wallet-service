package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailTool {
    public void execute() throws MessagingException, UnsupportedEncodingException;
}
