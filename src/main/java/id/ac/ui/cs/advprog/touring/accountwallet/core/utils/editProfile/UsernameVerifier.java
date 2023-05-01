package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;

import java.util.ArrayList;
import java.util.List;

public class UsernameVerifier implements IVerifier {
    IValidator usernameV;
    public UsernameVerifier(EditUsernameRequest request) {
        usernameV = new EditUsernameValidator(request);
    }

    public List<String> verify() {
        List<String> verifiedData = new ArrayList<>();
        String verified = usernameV.validate();
        verifiedData.add(verified);
        return verifiedData;
    }
}
