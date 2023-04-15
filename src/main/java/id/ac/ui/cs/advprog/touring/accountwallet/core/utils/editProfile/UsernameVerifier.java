package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;

import java.util.ArrayList;
import java.util.List;

public class UsernameVerifier {
    private EditUsernameRequest request;
    public UsernameVerifier(EditUsernameRequest request) {
        this.request = request;
        IValidator usernameV = new EditUsernameValidator(request);
    }

    public String verify() {
        // TODO: Complete this function
        return null;
    }
}
