package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.IVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.PersonalDataVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.UsernameVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile.UsernameAlreadyUsedException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile.UsernameEmptyInputException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EditProfileServiceImpl implements EditProfileService {
    private final UserRepository userRepository;

    @Override
    public EditProfileResponse editPersonalData(EditPersonalDataRequest request){
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.get();

        String fullName = (user.getFullName().isEmpty()) ? null : user.getFullName();
        String phoneNum = (user.getPhoneNum().isEmpty()) ? null : user.getPhoneNum();
        String birthDate = (user.getBirthDate().isEmpty()) ? null : user.getBirthDate();

        IVerifier verifier = new PersonalDataVerifier(request);
        try{
            List<String> verified = verifier.verify();
            fullName = (verified.get(0).equals("")) ? fullName : verified.get(0);
            phoneNum = (verified.get(1).equals("")) ? phoneNum : verified.get(1);
            birthDate = (verified.get(2).equals("")) ? birthDate : verified.get(2);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        user.setFullName(fullName);
        user.setPhoneNum(phoneNum);
        user.setBirthDate(birthDate);
        userRepository.save(user);

        EditProfileResponse response = EditProfileResponse.builder()
                .user(user)
                .message("Your profile editing has completed")
                .build();
        return response;
    }

    @Override
    public EditProfileResponse editUsername(EditUsernameRequest request){
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.get();

        String username = (user.getUsername().isEmpty()) ? null : user.getUsername();
        if (username == null || request.getUsername().equals("")) {
            throw new UsernameEmptyInputException();
        }

        if (userRepository.findByUsername(username).isPresent()){
            throw new UsernameAlreadyUsedException();
        }

        IVerifier verifier = new UsernameVerifier(request);
        try{
            List<String> verified = verifier.verify();
            username = verified.get(0);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        user.setUsername(username);
        userRepository.save(user);

        EditProfileResponse response = EditProfileResponse.builder()
                .user(user)
                .message("Your username editing has completed")
                .build();
        return response;
    }
}
