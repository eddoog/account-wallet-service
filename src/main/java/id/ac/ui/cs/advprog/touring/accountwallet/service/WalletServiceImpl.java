package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.IVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.PersonalDataVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile.UsernameVerifier;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.WalletRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.WalletResponse;
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
public class WalletServiceImpl implements WalletService {
    private final UserRepository userRepository;

    @Override
    public WalletResponse topUp(WalletRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }

        User user = userOptional.get();

        return WalletResponse.builder()
                .user(user)
                .message("Your profile editing has completed")
                .build();
    }
}
