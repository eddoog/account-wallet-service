package id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletApprovalRequest {
    private String email;
    private Integer amount;
    private Boolean approval;
}