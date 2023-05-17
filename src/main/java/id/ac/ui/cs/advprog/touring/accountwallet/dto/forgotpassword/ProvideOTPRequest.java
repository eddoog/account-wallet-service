package id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProvideOTPRequest {
    private String email;
}
