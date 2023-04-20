package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.ArrayList;

@NoArgsConstructor
public class CodeGenerator implements AuthTool {
    private final char[] allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public String generateToken(){
        var random = new SecureRandom();
        var token = new ArrayList<String>();
        for (var i = 0; i < 10; i++) {
            var randomCharIndex = random.nextInt(allowedCharacters.length);
            var randomChar = allowedCharacters[randomCharIndex];
            token.add(String.valueOf(randomChar));
        }
        return String.join("", token);
    }
    @Override
    public String execute() {
        return generateToken();
    }
}
