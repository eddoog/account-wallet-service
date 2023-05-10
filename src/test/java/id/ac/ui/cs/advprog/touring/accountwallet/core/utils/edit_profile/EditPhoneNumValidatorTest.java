package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidPhoneNumFormatException;
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
class EditPhoneNumValidatorTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    User userUpdatedButNull;
    User userSuccess;

    EditProfileResponse phoneNumResponseNull;
    EditProfileResponse phoneNumResponseSuccess;
    EditPersonalDataRequest phoneNumRequestExceedMaxNum;
    EditPersonalDataRequest phoneNumRequestRecedeMinNum;
    EditPersonalDataRequest phoneNumRequestNotUseInteger;
    EditPersonalDataRequest phoneNumRequestNull;
    EditPersonalDataRequest phoneNumRequestThatPass;

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
    void whenPhoneNumNullShouldReturnNull(){
        phoneNumRequestNull = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum(null)
                .build();

        userUpdatedButNull = User.builder()
                .email(userDummy.getEmail())
                .password(userDummy.getPassword())
                .username(userDummy.getUsername())
                .verificationCode(userDummy.getVerificationCode())
                .isEnabled(userDummy.getIsEnabled())
                .role(userDummy.getRole())
                .createdAt(userDummy.getCreatedAt())
                .phoneNum(null)
                .build();

        phoneNumResponseNull = EditProfileResponse.builder()
                .user(userUpdatedButNull)
                .message("Your profile editing has completed")
                .build();

        when(repository.findByEmail(phoneNumRequestNull.getEmail())).thenReturn(Optional.of(userDummy));

        EditProfileResponse result = service.editPersonalData(phoneNumRequestNull);
        Assertions.assertEquals(phoneNumResponseNull, result);
    }

    @Test
    void whenPhoneNumTrueShouldReturnSuccess(){
        phoneNumRequestThatPass = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("081617181920")
                .build();

        userSuccess = User.builder()
                .email(userDummy.getEmail())
                .password(userDummy.getPassword())
                .username(userDummy.getUsername())
                .verificationCode(userDummy.getVerificationCode())
                .isEnabled(userDummy.getIsEnabled())
                .role(userDummy.getRole())
                .createdAt(userDummy.getCreatedAt())
                .phoneNum("081617181920")
                .build();

        phoneNumResponseSuccess = EditProfileResponse.builder()
                .user(userSuccess)
                .message("Your profile editing has completed")
                .build();

        when(repository.findByEmail(phoneNumRequestThatPass.getEmail())).thenReturn(Optional.of(userDummy));

        EditProfileResponse result = service.editPersonalData(phoneNumRequestThatPass);
        Assertions.assertEquals(phoneNumResponseSuccess, result);
    }

    @Test
    void whenPhoneNumExceedsMaxNumThrowFormatException(){
        phoneNumRequestExceedMaxNum = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("0816171819202122")
                .build();

        when(repository.findByEmail(phoneNumRequestExceedMaxNum.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidPhoneNumFormatException.class, () -> {
            service.editPersonalData(phoneNumRequestExceedMaxNum);
        });
    }

    @Test
    void whenPhoneNumRecedeMinNumThrowFormatException(){
        phoneNumRequestRecedeMinNum = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("0816")
                .build();

        when(repository.findByEmail(phoneNumRequestRecedeMinNum.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidPhoneNumFormatException.class, () -> {
            service.editPersonalData(phoneNumRequestRecedeMinNum);
        });
    }

    @Test
    void whenPhoneNumNotUseIntegerThrowFormatException(){
        phoneNumRequestNotUseInteger = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("+6281617218567")
                .build();

        when(repository.findByEmail(phoneNumRequestNotUseInteger.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidPhoneNumFormatException.class, () -> {
            service.editPersonalData(phoneNumRequestNotUseInteger);
        });
    }
}