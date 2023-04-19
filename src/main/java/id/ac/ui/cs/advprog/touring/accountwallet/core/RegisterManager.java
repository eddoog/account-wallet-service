package id.ac.ui.cs.advprog.touring.accountwallet.core;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.CodeGenerator;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.PasswordEncryptor;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.AuthTool;

public class RegisterManager {

    private RegisterManager () {

    }
    static RegisterManager instance;

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
}
