package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditProfileServiceImpl implements EditProfileService {
    private final UserRepository userRepository;

    @Override
    public EditProfileResponse editPersonalData(Integer id, EditPersonalDataRequest request){
        return null;
    };

    @Override
    public EditProfileResponse editUsername(Integer id, EditUsernameRequest request){
        return null;
    }
}
