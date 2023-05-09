package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidPhoneNumFormatException;
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

class EditPhoneNumValidatorTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    User userEmptyString;
    User userSuccess;

    EditProfileResponse phoneNumResponseEmptyString;
    EditProfileResponse phoneNumResponseSuccess;
    EditPersonalDataRequest phoneNumRequestExceedsMaxNum;
    EditPersonalDataRequest phoneNumRequestRecedeMinNum;
    EditPersonalDataRequest phoneNumRequestNotUseInteger;
    EditPersonalDataRequest phoneNumRequestEmptyString;
    EditPersonalDataRequest phoneNumRequestThatPass;

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
                .phoneNum("081617181920")
                .build();

        phoneNumResponseEmptyString = EditProfileResponse.builder()
                .user(userEmptyString)
                .message("Your profile editing has completed")
                .build();

        phoneNumResponseSuccess = EditProfileResponse.builder()
                .user(userSuccess)
                .message("Your profile editing has completed")
                .build();

        phoneNumRequestExceedsMaxNum = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("0816171819202122")
                .build();

        phoneNumRequestRecedeMinNum = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("0816")
                .build();

        phoneNumRequestNotUseInteger = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("Nol1234567")
                .build();

        phoneNumRequestEmptyString = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("")
                .build();

        phoneNumRequestThatPass = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .phoneNum("081617181920")
                .build();
    }

    @Test
    void whenPhoneNumEmptyStringShouldReturnNull(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));
        when(repository.save(any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0, User.class));
        EditProfileResponse result = service.editPersonalData(phoneNumRequestEmptyString);
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(phoneNumResponseEmptyString, result);
    }

    @Test
    void whenPhoneNumTrueShouldReturnSuccess(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));
        when(repository.save(any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0, User.class));
        EditProfileResponse result = service.editPersonalData(phoneNumRequestThatPass);
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(phoneNumResponseEmptyString, result);
    }

    @Test
    void whenPhoneNumExceedsMaxNumThrowFormatException(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidPhoneNumFormatException.class, () -> {
            service.editPersonalData(phoneNumRequestExceedsMaxNum);
        });
    }

    @Test
    void whenPhoneNumRecedeMinNumThrowFormatException(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidPhoneNumFormatException.class, () -> {
            service.editPersonalData(phoneNumRequestRecedeMinNum);
        });
    }

    @Test
    void whenPhoneNumNotUseIntegerThrowFormatException(){
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InvalidPhoneNumFormatException.class, () -> {
            service.editPersonalData(phoneNumRequestNotUseInteger);
        });
    }
}