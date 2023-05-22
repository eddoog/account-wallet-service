package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.register;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.register.CodeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CodeGeneratorTest {
    @Test
    void testGenerateTokenLength() {
        CodeGenerator codeGenerator = new CodeGenerator();
        String token = codeGenerator.generateToken();
        assertNotNull(token);
        assertEquals(10, token.length());
    }
}
