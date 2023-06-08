package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile.IVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile.PersonalDataVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile.UsernameVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.ProfileRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.ProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
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
    public EditProfileResponse editPersonalData(EditPersonalDataRequest request) {
        String email = request.getEmail();
        var user = getUserByEmail(email);

        IVerifier verifier = new PersonalDataVerifier(request);
        List<String> verified = verifier.verify();

        user.setFullName(getUpdatedValue(user.getFullName(), verified.get(0)));
        user.setPhoneNum(getUpdatedValue(user.getPhoneNum(), verified.get(1)));
        user.setBirthDate(getUpdatedValue(user.getBirthDate(), verified.get(2)));
        user.setGender((request.getGender() != null && !request.getGender().equals("")) ? User.Gender.valueOf(request.getGender()) : user.getGender());
        user.setDomicile((request.getDomicile() != null && !request.getDomicile().equals("")) ? request.getDomicile() : user.getDomicile());
        userRepository.save(user);

        return EditProfileResponse.builder()
                .user(user)
                .message("Your profile editing has completed")
                .build();
    }

    @Override
    public EditProfileResponse editUsername(EditUsernameRequest request) {
        String email = request.getEmail();
        var user = getUserByEmail(email);

        String username = user.getUsername();

        String newUsername = request.getUsername();
        validateUsernameInput(username, newUsername);

        validateUsernameAvailability(newUsername);

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

    @Override
    public ProfileResponse getProfile(ProfileRequest request) {
        String email = request.getEmail();
        var user = getUserByEmail(email);

        return ProfileResponse.builder()
                .user(user)
                .build();
    }

    private User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return userOptional.get();
    }

    private String getUpdatedValue(String currentValue, String newValue) {
        return newValue != null && !newValue.equals("") ? newValue : currentValue;
    }

    private void validateUsernameInput(String currentUsername, String newUsername) {
        if (currentUsername == null || newUsername == null) {
            throw new UsernameEmptyInputException();
        }
    }

    private void validateUsernameAvailability(String newUsername) {
        if (userRepository.findByUsername(newUsername).isPresent()) {
            throw new UsernameAlreadyUsedException();
        }
    }
}
