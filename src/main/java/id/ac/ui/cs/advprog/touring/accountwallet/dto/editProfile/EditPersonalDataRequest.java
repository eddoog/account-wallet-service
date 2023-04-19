package id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditPersonalDataRequest {
    private String email;
    private String fullName;
    private String phoneNum;
    private String birthDate;
    private String gender;
    private String domicile;
}
