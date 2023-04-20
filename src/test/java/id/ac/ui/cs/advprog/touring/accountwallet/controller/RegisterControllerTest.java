package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.service.RegisterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {
    @InjectMocks
    private RegisterController registerController;
    private MockMvc mvc;
    @Mock
    private RegisterServiceImpl service;
    private ObjectMapper JSONSerializerDeserializer;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(registerController).build();
        JSONSerializerDeserializer = new ObjectMapper();
    }

    @Test
    void testRegister() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .username("testUsername")
                .email("test@test.com")
                .password("testPassword")
                .role("Customer")
                .build();

        RegisterResponse expectedResponse = RegisterResponse.builder().message("New Customer has been successfully made").build();

        when(service.register(request)).thenReturn(expectedResponse);

        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONSerializerDeserializer.writeValueAsString(request)))
                .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message").value("New Customer has been successfully made"));

        // verify that the register method was called with the expected request
        verify(service).register(request);
    }

    @Test
    void testVerify() throws Exception {
        String code = "TESCODE";

        RegisterResponse expectedResponse = RegisterResponse.builder().message("Your account has been verified").build();

        when(service.verify(code)).thenReturn(expectedResponse);

        mvc.perform(get("/api/v1/auth/verify")
                .param("code", "TESCODE"))
                .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message").value("Your account has been verified"));

        // Verify that the controller called the service with the correct code parameter
        verify(service).verify(code);
    }

}
