package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidUsernameFormatException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.UsernameAlreadyUsedException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.service.EditProfileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;

class EditUsernameValidatorTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    User userEmptyString;
    User userSuccess;
    User userFirstDouble;

    EditProfileResponse usernameResponseEmptyString;
    EditProfileResponse usernameResponseSuccess;
    EditUsernameRequest usernameRequestHasTaken;
    EditUsernameRequest usernameRequestFormatMismatch;
    EditUsernameRequest usernameRequestEmptyString;
    EditUsernameRequest usernameRequestThatPass;

    @BeforeEach
    void setup(){
        userDummy = User.builder()
                .email("test@example.com")
                .password("testPassword")
                .build();

        userEmptyString = User.builder()
                .email("test@example.com")
                .password("testPassword")
                .build();

        userSuccess = User.builder()
                .email("test@example.com")
                .password("testPassword")
                .username("John Doe")
                .build();

        userFirstDouble = User.builder()
                .email("test@exampledouble.com")
                .password("testPassword")
                .username("JohnDouble")
                .build();

        usernameResponseEmptyString = EditProfileResponse.builder()
                .user(userEmptyString)
                .message("Your profile editing has completed")
                .build();

        usernameResponseSuccess = EditProfileResponse.builder()
                .user(userSuccess)
                .message("Your profile editing has completed")
                .build();

        usernameRequestHasTaken = EditUsernameRequest.builder()
                .email("test@example.com")
                .username("JohnDouble")
                .build();

        usernameRequestFormatMismatch = EditUsernameRequest.builder()
                .email("test@example.com")
                .username("John()Doe#")
                .build();

        usernameRequestEmptyString = EditUsernameRequest.builder()
                .email("test@example.com")
                .username("")
                .build();

        usernameRequestThatPass = EditUsernameRequest.builder()
                .email("test@example.com")
                .username("John Doe")
                .build();
    }

    @Test
    void whenUsernameEmptyStringShouldReturnNull(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));
        when(repository.save(any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0, User.class));
        EditProfileResponse result = service.editUsername(usernameRequestEmptyString);
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(usernameResponseEmptyString, result);
    }

    @Test
    void whenUsernameTrueShouldReturnSuccess(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));
        when(repository.save(any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0, User.class));
        EditProfileResponse result = service.editUsername(usernameRequestThatPass);
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(usernameResponseEmptyString, result);
    }

    @Test
    void whenUsernameExceedsMaxNumThrowFormatException(){
        repository.save(userFirstDouble);
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(UsernameAlreadyUsedException.class, () -> {
            service.editUsername(usernameRequestHasTaken);
        });
        repository.delete(userFirstDouble);
    }

    @Test
    void whenUsernameFormatMismatchThrowFormatException(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidUsernameFormatException.class, () -> {
            service.editUsername(usernameRequestFormatMismatch);
        });
    }
}