package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.ProfileRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.ProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;

public interface EditProfileService {
    EditProfileResponse editPersonalData(EditPersonalDataRequest request);
    EditProfileResponse editUsername(EditUsernameRequest request);
    ProfileResponse getProfile(ProfileRequest request);
}

