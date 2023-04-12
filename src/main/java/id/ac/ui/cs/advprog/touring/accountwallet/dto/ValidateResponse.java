package id.ac.ui.cs.advprog.touring.accountwallet.dto;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateResponse {
    User user;
}
