package id.ac.ui.cs.advprog.touring.accountwallet.dto.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBuilderRequest {
    private String username;
    private String email;
    private String password;
    private UserType role;
    private String verificationCode;
}

