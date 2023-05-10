package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.AgeRestrictionException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidFullNameFormatException;
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
class EditFullNameValidatorTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    User userUpdatedButNull;
    User userSuccess;

    EditProfileResponse fullNameResponseNull;
    EditProfileResponse fullNameResponseSuccess;
    EditPersonalDataRequest fullNameRequestExceedsMaxNum;
    EditPersonalDataRequest fullNameRequestUsesSymbol;
    EditPersonalDataRequest fullNameRequestNull;
    EditPersonalDataRequest fullNameRequestThatPass;

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
    void whenFullNameNullShouldReturnNull(){
        fullNameRequestNull = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName(null)
                .build();

        userUpdatedButNull = User.builder()
                .email(userDummy.getEmail())
                .password(userDummy.getPassword())
                .username(userDummy.getUsername())
                .verificationCode(userDummy.getVerificationCode())
                .isEnabled(userDummy.getIsEnabled())
                .role(userDummy.getRole())
                .createdAt(userDummy.getCreatedAt())
                .fullName(null)
                .build();

        fullNameResponseNull = EditProfileResponse.builder()
                .user(userUpdatedButNull)
                .message("Your profile editing has completed")
                .build();

        when(repository.findByEmail(fullNameRequestNull.getEmail())).thenReturn(Optional.of(userDummy));

        EditProfileResponse result = service.editPersonalData(fullNameRequestNull);
        Assertions.assertEquals(fullNameResponseNull, result);
    }

    @Test
    void whenFullNameTrueShouldReturnSuccess(){
        fullNameRequestThatPass = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName("User John Doe")
                .build();

        userSuccess = User.builder()
                .email(userDummy.getEmail())
                .password(userDummy.getPassword())
                .username(userDummy.getUsername())
                .verificationCode(userDummy.getVerificationCode())
                .isEnabled(userDummy.getIsEnabled())
                .role(userDummy.getRole())
                .createdAt(userDummy.getCreatedAt())
                .fullName("User John Doe")
                .build();

        fullNameResponseSuccess = EditProfileResponse.builder()
                .user(userSuccess)
                .message("Your profile editing has completed")
                .build();

        when(repository.findByEmail(fullNameRequestThatPass.getEmail())).thenReturn(Optional.of(userDummy));

        EditProfileResponse result = service.editPersonalData(fullNameRequestThatPass);
        Assertions.assertEquals(fullNameResponseSuccess, result);
    }

    @Test
    void whenFullNameExceedsMaxNumThrowFormatException(){
        fullNameRequestExceedsMaxNum = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName("User John Doe Too")
                .build();

        when(repository.findByEmail(fullNameRequestExceedsMaxNum.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidFullNameFormatException.class, () -> {
            service.editPersonalData(fullNameRequestExceedsMaxNum);
        });
    }

    @Test
    void whenFullNameUsesSymbolThrowFormatException(){
        fullNameRequestUsesSymbol = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName("$01?01?1900#")
                .build();

        when(repository.findByEmail(fullNameRequestUsesSymbol.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidFullNameFormatException.class, () -> {
            service.editPersonalData(fullNameRequestUsesSymbol);
        });
    }

}