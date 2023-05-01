package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;

public interface EditProfileService {
    EditProfileResponse editPersonalData(EditPersonalDataRequest request);
    EditProfileResponse editUsername(EditUsernameRequest request);
}

