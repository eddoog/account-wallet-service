package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTransferRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.service.EditProfileServiceImpl;
import id.ac.ui.cs.advprog.touring.accountwallet.service.WalletService;
import id.ac.ui.cs.advprog.touring.accountwallet.service.WalletServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {
    @InjectMocks
    private WalletController walletController;

    @Mock
    private WalletServiceImpl walletService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testTopUp() throws Exception {
        WalletTopUpRequest request = WalletTopUpRequest.builder()
                .email("test@example.com")
                .amount(10)
                .currencyType("indonesian rupiah")
                .build();

        WalletResponse response = WalletResponse.builder()
                .message("Top up request of " + request.getAmount() + " IDR successful, waiting for approval")
                .build();

        when(walletService.topUp(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/auth/wallet/topup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Top up request of " + request.getAmount() +
                                " IDR successful, waiting for approval"));
    }

    @Test
    void testApproval() throws Exception {
        WalletApprovalRequest request = new WalletApprovalRequest();
        request.setEmail("test@example.com");
        request.setAmount(10);
        request.setApproval(Boolean.TRUE);

        WalletResponse response = WalletResponse.builder()
                .message("Approval accepted, "
                        + request.getAmount() + " IDR has been added to " + request.getEmail())
                .build();

        when(walletService.approval(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/auth/wallet/approval")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Approval accepted, "
                                + request.getAmount() + " IDR has been added to " + request.getEmail()));
    }

    @Test
    void testTransfer() throws Exception {
        WalletTransferRequest request = new WalletTransferRequest();
        request.setEmail("test@example.com");
        request.setAmount(10);

        WalletResponse response = WalletResponse.builder()
                .message("Transaction successful, " + 10 + " IDR has been deducted")
                .build();

        when(walletService.transfer(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/auth/wallet/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Transaction successful, " + 10 + " IDR has been deducted"));
    }
}