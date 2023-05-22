package id.ac.ui.cs.advprog.touring.accountwallet.dto.internal;

import java.util.List;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {
    List<User> users;
}
