package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.EditPersonalDataResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.*;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class EditProfileServiceImpl implements EditProfileService {
    private final UserRepository userRepository;

    @Override
    public EditPersonalDataResponse editPersonalData(Integer id, EditPersonalDataRequest request){
        return null;
    };

    @Override
    public EditUsernameResponse editUsername(Integer id, EditUsernameRequest request){
        return null;
    }
}
