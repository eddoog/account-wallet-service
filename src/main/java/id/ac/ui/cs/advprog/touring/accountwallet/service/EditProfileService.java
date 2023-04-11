package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.*;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;

public interface EditProfileService {
    public EditPersonalDataResponse editPersonalData(Integer id, EditPersonalDataRequest request);
    public EditUsernameResponse editUsername(Integer id, EditUsernameRequest request);
}

