package id.ac.ui.cs.advprog.touring.accountwallet.core;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.register.CodeGenerator;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.register.PasswordEncryptor;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.AuthTool;

import java.util.regex.Pattern;

public class RegisterManager {

    private RegisterManager () {

    }
    static RegisterManager instance;
    private static final int MIN_PASSWORD_CHARACTER = 5;
    private static final int MAX_PASSWORD_CHARACTER = 20;
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static RegisterManager getInstance() {
        if (instance == null)  instance = new RegisterManager();
        return instance;
    }
    public String generateVerificationCode() {
        AuthTool codeGenerator = new CodeGenerator();
        return codeGenerator.execute();
    }
    public String encryptPassword(String password) {
        AuthTool passwordEncryptor = new PasswordEncryptor(password);
        return passwordEncryptor.execute();
    }
    public boolean checkEmailValid(String email) {return !(VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches());}
    public boolean checkTrimValid(String chr) {return !(chr.equals(chr.trim()));}
    public boolean checkPasswordLength(String password) {return !(password.length() >= MIN_PASSWORD_CHARACTER && password.length() <= MAX_PASSWORD_CHARACTER);}
}
