package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EditProfileControllerTest {
    @InjectMocks
    private AuthController authController;

    @Mock
    private EditProfileServiceImpl editProfileService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testEditPersonalData() throws Exception {
        EditPersonalDataRequest request = new EditPersonalDataRequest();
        request.setEmail("test@example.com");
        request.setFullName("Test Full Name");
        request.setPhoneNum("081616161616");
        request.setBirthDate("01/01/2001");
        request.setGender("Male");
        request.setDomicile("Jakarta");

        EditProfileResponse response = new EditProfileResponse();

        when(editProfileService.editPersonalData(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/update/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Your profile editing has completed"));
    }

    void testEditUsername() throws Exception {
        EditUsernameRequest request = new EditUsernameRequest();
        request.setEmail("test@example.com");
        request.setUsername("username");

        EditProfileResponse response = new EditProfileResponse();

        when(editProfileService.editUsername(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/update/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Your username editing has completed"));
    }
}
