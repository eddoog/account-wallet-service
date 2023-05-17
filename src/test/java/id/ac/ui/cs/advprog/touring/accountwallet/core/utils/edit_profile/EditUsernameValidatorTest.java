package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidUsernameFormatException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.UsernameAlreadyUsedException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.UsernameEmptyInputException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.service.EditProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditUsernameValidatorTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    User userDummyDouble;
    User userSuccess;
    EditProfileResponse usernameResponseSuccess;
    EditUsernameRequest userNotFound;
    EditUsernameRequest usernameRequestNull;
    EditUsernameRequest usernameRequestTakenUsername;
    EditUsernameRequest usernameRequestFormatMismatch;
    EditUsernameRequest usernameRequestThatPass;

    @BeforeEach
    void setup(){
        userDummy = User.builder()
                .email("test@example.com")
                .password("testPassword")
                .username("testUsername")
                .verificationCode("0123456789")
                .isEnabled(false)
                .role(UserType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        userDummyDouble = User.builder()
                .email("test@example2.com")
                .password("testPassword2")
                .username("testUsername2")
                .verificationCode("0123456789")
                .isEnabled(false)
                .role(UserType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void whenUsernameNullShouldThrowException(){
        usernameRequestNull = EditUsernameRequest.builder()
                .email("test@example.com")
                .username(null)
                .build();

        when(repository.findByEmail(usernameRequestNull.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(UsernameEmptyInputException.class, () -> {
            service.editUsername(usernameRequestNull);
        });
    }

    @Test
    void whenUsernameTrueShouldReturnSuccess(){
        usernameRequestThatPass = EditUsernameRequest.builder()
                .email("test@example.com")
                .username("JohnDoe_")
                .build();

        userSuccess = User.builder()
                .email(userDummy.getEmail())
                .password(userDummy.getPassword())
                .username("JohnDoe_")
                .verificationCode(userDummy.getVerificationCode())
                .isEnabled(userDummy.getIsEnabled())
                .role(userDummy.getRole())
                .createdAt(userDummy.getCreatedAt())
                .build();

        usernameResponseSuccess = EditProfileResponse.builder()
                .user(userSuccess)
                .message("Your username editing has completed")
                .build();

        when(repository.findByEmail(usernameRequestThatPass.getEmail())).thenReturn(Optional.of(userDummy));

        EditProfileResponse result = service.editUsername(usernameRequestThatPass);
        Assertions.assertEquals(usernameResponseSuccess, result);
    }

    @Test
    void whenUsernameHasBeenTakenThrowException(){
        usernameRequestTakenUsername = EditUsernameRequest.builder()
                .email("test@example.com")
                .username("testUsername2")
                .build();

        when(repository.findByEmail(usernameRequestTakenUsername.getEmail())).thenReturn(Optional.of(userDummy));
        when(repository.findByUsername(usernameRequestTakenUsername.getUsername())).thenReturn(Optional.of(userDummyDouble));

        Assertions.assertThrows(UsernameAlreadyUsedException.class, () -> {
            service.editUsername(usernameRequestTakenUsername);
        });
    }

    @Test
    void whenUsernameFormatMismatchThrowFormatException(){
        usernameRequestFormatMismatch = EditUsernameRequest.builder()
                .email("test@example.com")
                .username("#JohnDoe")
                .build();

        when(repository.findByEmail(usernameRequestFormatMismatch.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidUsernameFormatException.class, () -> {
            service.editUsername(usernameRequestFormatMismatch);
        });
    }

    @Test
    void whenNoUserFoundThrowException(){
        userNotFound = EditUsernameRequest.builder()
                .email("test@example.coma")
                .username("testUsername")
                .build();

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            service.editUsername(userNotFound);
        });
    }

}