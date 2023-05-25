package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.ProfileRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.ProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProfileServiceTest {

    @InjectMocks
    private EditProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userDummy;
    ProfileRequest userSuccessRequest;
    ProfileRequest userNotFoundRequest;
    ProfileResponse userSuccessResponse;

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
    void whenNoUserFoundThrowException(){
        userNotFoundRequest = ProfileRequest.builder()
                .email("test@example.coma")
                .build();

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            service.getProfile(userNotFoundRequest);
        });
    }

    @Test
    void whenUserFoundGetProfile(){
        userSuccessRequest = ProfileRequest.builder()
                .email("test@example.com")
                .build();

        userSuccessResponse = ProfileResponse.builder()
                .user(userDummy)
                .build();

        when(repository.findByEmail(userSuccessRequest.getEmail())).thenReturn(Optional.of(userDummy));

        ProfileResponse result = service.getProfile(userSuccessRequest);
        Assertions.assertEquals(userSuccessResponse, result);
        Assertions.assertEquals(userDummy.getEmail(), result.getUser().getEmail());
        Assertions.assertEquals(userDummy.getUsername(), result.getUser().getUsername());
        Assertions.assertEquals(userDummy.getRole(), result.getUser().getRole());
        Assertions.assertEquals(userDummy.getCreatedAt(), result.getUser().getCreatedAt());
    }

    @Test
    void whenDifferentUserProfileFound() {
        User differentUser = User.builder()
                .email("different@example.com")
                .password("differentPassword")
                .username("differentUsername")
                .verificationCode("9876543210")
                .isEnabled(true)
                .role(UserType.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();

        ProfileRequest differentUserRequest = ProfileRequest.builder()
                .email("test@example.com")
                .build();

        ProfileResponse differentUserResponse = ProfileResponse.builder()
                .user(differentUser)
                .build();

        when(repository.findByEmail(differentUserRequest.getEmail())).thenReturn(Optional.of(differentUser));

        ProfileResponse result = service.getProfile(differentUserRequest);
        Assertions.assertEquals(differentUserResponse, result);
    }
}