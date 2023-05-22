package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.internal.UsersRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.internal.UsersResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.service.InternalService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class InternalControllerTest {
    @InjectMocks
    private InternalController internalController;

    @Mock
    private InternalService internalService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(internalController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testLogin() throws Exception {
        UsersRequest request = new UsersRequest();
        request.setUserIds(new Integer[] { 1 });

        UsersResponse response = new UsersResponse();
        var user = User.builder()
                .id(1)
                .username("uniqueuser")
                .email("uniqueuser@example.com")
                .password("password")
                .role(UserType.TOURGUIDE)
                .verificationCode("0123456789")
                .isEnabled(false)
                .createdAt(LocalDateTime.now().minusMinutes(10))
                .build();

        var users = new ArrayList<User>();
        users.add(user);
        response.setUsers(users);

        when(internalService.fetchUserIds(request.getUserIds())).thenReturn(response);

        mockMvc.perform(post("/api/v1/internal/fetch-uids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users[0].username").value("uniqueuser"));
    }
}
