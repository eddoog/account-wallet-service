package id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUsernameRequest {
    private String phoneNum;
    private String name;
    private String gender;
    private String domicile;
}
