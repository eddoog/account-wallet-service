package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile.IVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile.PersonalDataVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile.UsernameVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.UsernameAlreadyUsedException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.UsernameEmptyInputException;
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
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }
        var user = userOptional.get();

        String fullName = (user.getFullName() == null) ? null : user.getFullName();
        String phoneNum = (user.getPhoneNum() == null) ? null : user.getPhoneNum();
        String birthDate = (user.getBirthDate() == null) ? null : user.getBirthDate();
        String gender = (request.getGender().equals("")) ? null : request.getGender();
        String domicile = (request.getDomicile().equals("")) ? null : request.getDomicile();


        IVerifier verifier = new PersonalDataVerifier(request);
        List<String> verified = verifier.verify();
        fullName = (verified.get(0).equals("")) ? fullName : verified.get(0);
        phoneNum = (verified.get(1).equals("")) ? phoneNum : verified.get(1);
        birthDate = (verified.get(2).equals("")) ? birthDate : verified.get(2);

        user.setFullName(fullName);
        user.setPhoneNum(phoneNum);
        user.setBirthDate(birthDate);
        user.setGender(gender);
        user.setDomicile(domicile);
        userRepository.save(user);

        return EditProfileResponse.builder()
                .user(user)
                .message("Your profile editing has completed")
                .build();
    }

    @Override
    public EditProfileResponse editUsername(EditUsernameRequest request){
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }
        var user = userOptional.get();

        String username = (user.getUsername().isEmpty()) ? null : user.getUsername();

        String newUsername = request.getUsername();
        if (username == null || newUsername.equals("")) {
            throw new UsernameEmptyInputException();
        }

        if (userRepository.findByUsername(newUsername).isPresent()){
            throw new UsernameAlreadyUsedException();
        }

        IVerifier verifier = new UsernameVerifier(request);
        List<String> verified = verifier.verify();
        newUsername = verified.get(0);

        user.setUsername(newUsername);
        userRepository.save(user);

        return EditProfileResponse.builder()
                .user(user)
                .message("Your username editing has completed")
                .build();
    }
}
