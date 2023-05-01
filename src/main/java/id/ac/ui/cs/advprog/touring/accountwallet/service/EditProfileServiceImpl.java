package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.IVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.PersonalDataVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.UsernameVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.UserNotFoundException;
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
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }
        User user = userOptional.get();

        String fullName = (user.getFullName() == null) ? null : user.getFullName();
        String phoneNum = (user.getPhoneNum() == null) ? null : user.getPhoneNum();
        String birthDate = (user.getBirthDate() == null) ? null : user.getBirthDate();
        String gender = (request.getGender().equals("")) ? null : request.getGender();
        String domicile = (request.getDomicile().equals("")) ? null : request.getDomicile();


        IVerifier verifier = new PersonalDataVerifier(request);
        try{
            List<String> verified = verifier.verify();
            fullName = (verified.get(0).equals("")) ? fullName : verified.get(0);
            phoneNum = (verified.get(1).equals("")) ? phoneNum : verified.get(1);
            birthDate = (verified.get(2).equals("")) ? birthDate : verified.get(2);
        } catch (RuntimeException e) {
            throw e;
        }

        user.setFullName(fullName);
        user.setPhoneNum(phoneNum);
        user.setBirthDate(birthDate);
        user.setGender(gender);
        user.setDomicile(domicile);
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
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }
        User user = userOptional.get();

        String username = (user.getUsername().isEmpty()) ? null : user.getUsername();

        String newUsername = request.getUsername();
        if (username == null || newUsername.equals("")) {
            throw new UsernameEmptyInputException();
        }

        if (userRepository.findByUsername(newUsername).isPresent()){
            throw new UsernameAlreadyUsedException();
        }

        IVerifier verifier = new UsernameVerifier(request);
        try{
            List<String> verified = verifier.verify();
            newUsername = verified.get(0);
        } catch (RuntimeException e) {
            throw e;
        }

        user.setUsername(newUsername);
        userRepository.save(user);

        EditProfileResponse response = EditProfileResponse.builder()
                .user(user)
                .message("Your username editing has completed")
                .build();
        return response;
    }
}
