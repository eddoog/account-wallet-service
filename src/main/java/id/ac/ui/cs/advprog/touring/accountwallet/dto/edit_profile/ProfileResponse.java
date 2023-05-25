package id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileResponse {
    private User user;
}
