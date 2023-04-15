package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;

public interface EditProfileService {
    public EditProfileResponse editPersonalData(Integer id, EditPersonalDataRequest request);
    public EditProfileResponse editUsername(Integer id, EditUsernameRequest request);
}

