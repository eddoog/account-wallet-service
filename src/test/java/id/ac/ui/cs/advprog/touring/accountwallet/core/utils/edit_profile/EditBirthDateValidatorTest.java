package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.AgeRestrictionException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidBirthDateFormatException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.service.EditProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EditBirthDateValidatorTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    User userEmptyString;
    User userSuccess;

    EditProfileResponse birthDateResponseEmptyString;
    EditProfileResponse birthDateResponseSuccess;
    EditPersonalDataRequest birthDateRequestWithWrongFormat;
    EditPersonalDataRequest birthDateRequestRestrictedByAge;
    EditPersonalDataRequest birthDateRequestEmptyString;
    EditPersonalDataRequest birthDateRequestThatPass;

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
                .birthDate("01-01-2001")
                .build();

        birthDateResponseEmptyString = EditProfileResponse.builder()
                .user(userEmptyString)
                .message("Your profile editing has completed")
                .build();

        birthDateResponseSuccess = EditProfileResponse.builder()
                .user(userSuccess)
                .message("Your profile editing has completed")
                .build();

        birthDateRequestWithWrongFormat = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate("01-01-2001")
                .build();

        birthDateRequestRestrictedByAge = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate("01/01/1900")
                .build();

        birthDateRequestEmptyString = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate("")
                .build();

        birthDateRequestThatPass = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate("01/01/2001")
                .build();
    }

    @Test
    void whenBirthDateEmptyStringShouldReturnNull(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));
        when(repository.save(any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0, User.class));
        EditProfileResponse result = service.editPersonalData(birthDateRequestEmptyString);
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(birthDateResponseEmptyString, result);
    }

    @Test
    void whenBirthDateTrueShouldReturnSuccess(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));
        when(repository.save(any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0, User.class));
        EditProfileResponse result = service.editPersonalData(birthDateRequestThatPass);
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(birthDateResponseEmptyString, result);
    }

    @Test
    void whenBirthDateWrongFormatThrowFormatException(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidBirthDateFormatException.class, () -> {
            service.editPersonalData(birthDateRequestWithWrongFormat);
        });
    }

    @Test
    void whenBirthDatePastAgeLimitThrowAgeRestrictException(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(AgeRestrictionException.class, () -> {
            service.editPersonalData(birthDateRequestRestrictedByAge);
        });
    }

}