package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.ProfileRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.ProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.service.EditProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EditProfileControllerTest {
    @InjectMocks
    private EditProfileController editProfileController;

    @Mock
    private EditProfileServiceImpl editProfileService;
    @Mock
    private UserRepository repository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(editProfileController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetProfile() throws Exception {
        ProfileRequest request = ProfileRequest.builder()
                .email("test@example.com")
                .build();

        ProfileResponse response = ProfileResponse.builder()
                .build();

        when(editProfileService.getProfile(request)).thenReturn(response);

        mockMvc.perform(get("/api/v1/auth/editProfile/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testEditPersonalData() throws Exception {
        EditPersonalDataRequest request = EditPersonalDataRequest.builder()
                .email("test@example.com")
                .fullName("Test Full Name")
                .phoneNum("081616161616")
                .birthDate("01/01/2001")
                .gender("Male")
                .domicile("Jakarta")
                .build();

        EditProfileResponse response = EditProfileResponse.builder()
                .message("Your profile editing has completed")
                .build();

        when(editProfileService.editPersonalData(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/auth/editProfile/update/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Your profile editing has completed"));
    }

    @Test
    void testEditUsername() throws Exception {
        EditUsernameRequest request = new EditUsernameRequest();
        request.setEmail("test@example.com");
        request.setUsername("username");

        EditProfileResponse response = EditProfileResponse.builder()
                .message("Your username editing has completed")
                .build();

        when(editProfileService.editUsername(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/auth/editProfile/update/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Your username editing has completed"));
    }
}
