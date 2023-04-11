package id.ac.ui.cs.advprog.touring.accountwallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditPersonalDataRequest {
    private String phoneNum;
    private String name;
    private String gender;
    private String domicile;
}
