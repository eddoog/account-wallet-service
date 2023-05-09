package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidFullNameFormatException;
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

class EditFullNameValidatorTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    User userEmptyString;
    User userSuccess;

    EditProfileResponse fullNameResponseEmptyString;
    EditProfileResponse fullNameResponseSuccess;
    EditPersonalDataRequest fullNameRequestExceedsMaxNum;
    EditPersonalDataRequest fullNameRequestUsesSymbol;
    EditPersonalDataRequest fullNameRequestEmptyString;
    EditPersonalDataRequest fullNameRequestThatPass;

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
                .fullName("John Doe")
                .build();

        fullNameResponseEmptyString = EditProfileResponse.builder()
                .user(userEmptyString)
                .message("Your profile editing has completed")
                .build();

        fullNameResponseSuccess = EditProfileResponse.builder()
                .user(userSuccess)
                .message("Your profile editing has completed")
                .build();

        fullNameRequestExceedsMaxNum = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName("John Doe Test Subject")
                .build();

        fullNameRequestUsesSymbol = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName("John()Doe#")
                .build();

        fullNameRequestEmptyString = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName("")
                .build();

        fullNameRequestThatPass = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName("John Doe")
                .build();
    }

    @Test
    void whenFullNameEmptyStringShouldReturnNull(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));
        when(repository.save(any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0, User.class));
        EditProfileResponse result = service.editPersonalData(fullNameRequestEmptyString);
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(fullNameResponseEmptyString, result);
    }

    @Test
    void whenFullNameTrueShouldReturnSuccess(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));
        when(repository.save(any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0, User.class));
        EditProfileResponse result = service.editPersonalData(fullNameRequestThatPass);
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(fullNameResponseEmptyString, result);
    }

    @Test
    void whenFullNameExceedsMaxNumThrowFormatException(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidFullNameFormatException.class, () -> {
            service.editPersonalData(fullNameRequestExceedsMaxNum);
        });
    }

    @Test
    void whenFullNameUsesSymbolThrowFormatException(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidFullNameFormatException.class, () -> {
            service.editPersonalData(fullNameRequestUsesSymbol);
        });
    }
}