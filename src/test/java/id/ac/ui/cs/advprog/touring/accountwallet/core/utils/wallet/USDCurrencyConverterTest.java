package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.wallet;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet.AmountNegativeException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet.AmountNullException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.TopUpApprovalRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.service.WalletServiceImpl;
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
class USDCurrencyConverterTest {
    @InjectMocks
    private WalletServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private TopUpApprovalRepository topUpApprovalRepository;

    User userDummy;
    WalletResponse amountResponseSuccess;
    WalletTopUpRequest amountRequestThatPass;
    WalletTopUpRequest amountNegative;
    WalletTopUpRequest amountNull;

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
    void whenAmountIsNegativeShouldThrowException(){
        amountNegative = WalletTopUpRequest.builder()
                .email("test@example.com")
                .amount(-1)
                .currencyType("United States Dollar")
                .build();

        when(repository.findByEmail(amountNegative.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(AmountNegativeException.class, () -> {
            service.topUp(amountNegative);
        });
    }

    @Test
    void whenAmountIsNullShouldThrowException(){
        amountNull = WalletTopUpRequest.builder()
                .email("test@example.com")
                .amount(null)
                .currencyType("United States Dollar")
                .build();

        when(repository.findByEmail(amountNull.getEmail())).thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(AmountNullException.class, () -> {
            service.topUp(amountNull);
        });
    }

    @Test
    void whenAmountTrueShouldReturnSuccess(){
        amountRequestThatPass = WalletTopUpRequest.builder()
                .email("test@example.com")
                .amount(1)
                .currencyType("United States Dollar")
                .build();

        amountResponseSuccess = WalletResponse.builder()
                .user(userDummy)
                .message("Top up request of " + 14500 +
                        " IDR successful, waiting for approval")
                .build();

        when(repository.findByEmail(amountRequestThatPass.getEmail())).thenReturn(Optional.of(userDummy));

        WalletResponse result = service.topUp(amountRequestThatPass);
        Assertions.assertEquals(amountResponseSuccess, result);
    }
}
