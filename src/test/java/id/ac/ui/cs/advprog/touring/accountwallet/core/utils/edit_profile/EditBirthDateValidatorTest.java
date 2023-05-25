package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.AgeRestrictionException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidBirthDateFormatException;
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
class EditBirthDateValidatorTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    User userUpdatedButNull;
    User userSuccess;

    EditProfileResponse birthDateResponseNull;
    EditProfileResponse birthDateResponseSuccess;
    EditPersonalDataRequest userNotFound;
    EditPersonalDataRequest birthDateRequestWithWrongFormat;
    EditPersonalDataRequest birthDateRequestRestrictedByAge;
    EditPersonalDataRequest birthDateRequestNull;
    EditPersonalDataRequest birthDateRequestThatPass;

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
    }

    @Test
    void whenBirthDateNullShouldReturnNull(){
        birthDateRequestNull = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate(null)
                .build();

        userUpdatedButNull = User.builder()
                .email(userDummy.getEmail())
                .password(userDummy.getPassword())
                .username(userDummy.getUsername())
                .verificationCode(userDummy.getVerificationCode())
                .isEnabled(userDummy.getIsEnabled())
                .role(userDummy.getRole())
                .createdAt(userDummy.getCreatedAt())
                .birthDate(null)
                .build();

        birthDateResponseNull = EditProfileResponse.builder()
                .user(userUpdatedButNull)
                .message("Your profile editing has completed")
                .build();

        when(repository.findByEmail(birthDateRequestNull.getEmail())).thenReturn(Optional.of(userDummy));

        EditProfileResponse result = service.editPersonalData(birthDateRequestNull);
        Assertions.assertEquals(birthDateResponseNull, result);
    }

    @Test
    void whenBirthDateTrueShouldReturnSuccess(){
        birthDateRequestThatPass = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate("01/01/2001")
                .build();

        userSuccess = User.builder()
                .email(userDummy.getEmail())
                .password(userDummy.getPassword())
                .username(userDummy.getUsername())
                .verificationCode(userDummy.getVerificationCode())
                .isEnabled(userDummy.getIsEnabled())
                .role(userDummy.getRole())
                .createdAt(userDummy.getCreatedAt())
                .birthDate("01/01/2001")
                .build();

        birthDateResponseSuccess = EditProfileResponse.builder()
                .user(userSuccess)
                .message("Your profile editing has completed")
                .build();

        when(repository.findByEmail(birthDateRequestThatPass.getEmail())).thenReturn(Optional.of(userDummy));

        EditProfileResponse result = service.editPersonalData(birthDateRequestThatPass);
        Assertions.assertEquals(birthDateResponseSuccess, result);
    }

    @Test
    void whenBirthDateWrongFormatThrowFormatException(){
        birthDateRequestWithWrongFormat = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate("01-01-2001")
                .build();

        when(repository.findByEmail(birthDateRequestWithWrongFormat.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidBirthDateFormatException.class, () -> {
            service.editPersonalData(birthDateRequestWithWrongFormat);
        });
    }

    @Test
    void whenBirthDatePastAgeLimitThrowAgeRestrictException(){
        birthDateRequestRestrictedByAge = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate("01/01/1900")
                .build();

        when(repository.findByEmail(birthDateRequestRestrictedByAge.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(AgeRestrictionException.class, () -> {
            service.editPersonalData(birthDateRequestRestrictedByAge);
        });
    }

    @Test
    void whenBirthDateBelowAgeLimitThrowAgeRestrictException(){
        birthDateRequestRestrictedByAge = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .birthDate("01/01/2100")
                .build();

        when(repository.findByEmail(birthDateRequestRestrictedByAge.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(AgeRestrictionException.class, () -> {
            service.editPersonalData(birthDateRequestRestrictedByAge);
        });
    }

    @Test
    void whenNoUserFoundThrowException(){
        userNotFound = EditPersonalDataRequest.builder()
                .email("test@example.coma")
                .birthDate("10/10/1000")
                .build();

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            service.editPersonalData(userNotFound);
        });
    }
}