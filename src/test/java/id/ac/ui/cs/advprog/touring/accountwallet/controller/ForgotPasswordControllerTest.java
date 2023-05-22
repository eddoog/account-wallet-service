package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.CheckOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ForgotPasswordResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.forgotpassword.ProvideOTPRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.service.ForgotPasswordServiceImpl;
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
class ForgotPasswordControllerTest {
    @InjectMocks
    private ForgotPasswordController forgotPasswordController;
    private MockMvc mvc;
    @Mock
    private ForgotPasswordServiceImpl service;

    private ObjectMapper JSONSerializerDeserializer;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(forgotPasswordController).build();
        JSONSerializerDeserializer = new ObjectMapper();
    }

    @Test
    void testProvideOTP() throws Exception {
        var request = ProvideOTPRequest.builder().email("testEmail@test.com").build();
        var response = ForgotPasswordResponse.builder().message("Please check your email to get the OTP code").build();

        when(service.provideOTP(request)).thenReturn(response);

        mvc.perform(post("/api/v1/auth/forgot-password/provide-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONSerializerDeserializer.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Please check your email to get the OTP code"));
    }

    @Test
    void testCheckOTP() throws Exception {
        var request = CheckOTPRequest.builder().otpCode(123456).email("testEmail@test.com").build();
        var response = ForgotPasswordResponse.builder().message("Please fill out the form to change your password").build();

        when(service.checkOTP(request)).thenReturn(response);

        mvc.perform(post("/api/v1/auth/forgot-password/check-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONSerializerDeserializer.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Please fill out the form to change your password"));
    }

    @Test
    void testChangePassword() throws Exception {
        var request = ForgotPasswordRequest.builder().newPassword("testPassword").email("testEmail@test.com").build();
        var response = ForgotPasswordResponse.builder().message("Your password has been changed successfully").build();

        when(service.changePassword(request)).thenReturn(response);

        mvc.perform(post("/api/v1/auth/forgot-password/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONSerializerDeserializer.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Your password has been changed successfully"));
    }

}
