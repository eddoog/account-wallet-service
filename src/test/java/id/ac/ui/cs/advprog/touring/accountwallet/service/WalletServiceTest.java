package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTransferRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet.*;
import id.ac.ui.cs.advprog.touring.accountwallet.model.TopUpApproval;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.TopUpApprovalRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.TransactionRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WalletServiceTest {
    @InjectMocks
    private WalletServiceImpl service;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private TopUpApprovalRepository mockTopUpApprovalRepository;
    @Mock
    private TransactionRepository mockTransactionRepository;
    User userDummy;
    TopUpApproval topUpApprovalDummy;
    WalletResponse responseSuccess;
    WalletTopUpRequest topUpEmailNotFound, topUpCurrencyTypeNotSupported;
    WalletTransferRequest transferEmailNotFound, transferAmountNull,
            transferAmountNegative, transferAmountMoreThanBalance, transferSuccessful;
    WalletApprovalRequest approvalEmailNotFound, approvalRequestRejected,
            approvalIncorrectData, approvalSuccessful;

    @BeforeEach
    void setup() {
        userDummy = User.builder()
                .email("test@example.com")
                .password("testPassword")
                .username("testUsername")
                .verificationCode("0123456789")
                .isEnabled(false)
                .role(UserType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .walletAmount(100)
                .build();

        topUpApprovalDummy = TopUpApproval.builder()
                .user(userDummy)
                .transactionAmount(50)
                .build();
    }

    @Test
    void whenTopUpEmailNotFoundShouldThrowException() {
        topUpEmailNotFound = WalletTopUpRequest.builder()
                .email("test@example.coma")
                .build();

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            service.topUp(topUpEmailNotFound);
        });
    }

    @Test
    void whenTopUpCurrencyTypeNotSupportedShouldThrowException() {
        topUpCurrencyTypeNotSupported = WalletTopUpRequest.builder()
                .email("test@example.com")
                .amount(1)
                .currencyType("Yen")
                .build();

        when(mockUserRepository.findByEmail(topUpCurrencyTypeNotSupported.getEmail()))
                .thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(CurrencyNotSupportedException.class, () -> {
            service.topUp(topUpCurrencyTypeNotSupported);
        });
    }

    @Test
    void whenTransferEmailNotFoundShouldThrowException() {
        transferEmailNotFound = WalletTransferRequest.builder()
                .email("test@example.coma")
                .build();

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            service.transfer(transferEmailNotFound);
        });
    }

    @Test
    void whenTransferAmountIsNullShouldThrowException() {
        transferAmountNull = WalletTransferRequest.builder()
                .email("test@example.com")
                .amount(null)
                .build();

        when(mockUserRepository.findByEmail(transferAmountNull.getEmail()))
                .thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(AmountNullException.class, () -> {
            service.transfer(transferAmountNull);
        });
    }

    @Test
    void whenTransferAmountIsNegativeShouldThrowException() {
        transferAmountNegative = WalletTransferRequest.builder()
                .email("test@example.com")
                .amount(-1)
                .build();

        when(mockUserRepository.findByEmail(transferAmountNegative.getEmail()))
                .thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(AmountNegativeException.class, () -> {
            service.transfer(transferAmountNegative);
        });
    }

    @Test
    void whenTransferAmountIsMoreThanBalanceShouldThrowException() {
        transferAmountMoreThanBalance = WalletTransferRequest.builder()
                .email("test@example.com")
                .amount(150)
                .build();

        when(mockUserRepository.findByEmail(transferAmountMoreThanBalance.getEmail()))
                .thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(InsufficientFundsException.class, () -> {
            service.transfer(transferAmountMoreThanBalance);
        });
    }

    @Test
    void whenTransferTrueShouldReturnSuccess() {
        transferSuccessful = WalletTransferRequest.builder()
                .email("test@example.com")
                .amount(50)
                .build();

        responseSuccess = WalletResponse.builder()
                .user(userDummy)
                .message("Transaction successful, "
                        + transferSuccessful.getAmount() + " IDR has been deducted")
                .build();

        when(mockUserRepository.findByEmail(transferSuccessful.getEmail())).thenReturn(Optional.of(userDummy));

        WalletResponse result = service.transfer(transferSuccessful);
        Assertions.assertEquals(responseSuccess, result);
    }

    @Test
    void whenApprovalEmailNotFoundShouldThrowException() {
        approvalEmailNotFound = WalletApprovalRequest.builder()
                .email("test@example.coma")
                .build();

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            service.approval(approvalEmailNotFound);
        });
    }

    @Test
    void whenApprovalRequestIsFalseShouldThrowException() {
        approvalRequestRejected = WalletApprovalRequest.builder()
                .email("test@example.com")
                .transactionId(50)
                .approval(Boolean.FALSE)
                .build();

        when(mockUserRepository.findByEmail(approvalRequestRejected.getEmail()))
                .thenReturn(Optional.of(userDummy));

        when(mockTopUpApprovalRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(topUpApprovalDummy));
    }

    @Test
    void whenApprovalIncorrectDataShouldThrowException() {
        approvalIncorrectData = WalletApprovalRequest.builder()
                .email("test@example.com")
                .transactionId(100)
                .approval(Boolean.TRUE)
                .build();

        when(mockUserRepository.findByEmail(approvalIncorrectData.getEmail()))
                .thenReturn(Optional.of(userDummy));

        Assertions.assertThrows(IncorrectDataException.class, () -> {
            service.approval(approvalIncorrectData);
        });
    }

    @Test
    void whenApprovalCorrectShouldReturnSuccess() {
        approvalSuccessful = WalletApprovalRequest.builder()
                .email("test@example.com")
                .transactionId(50)
                .approval(Boolean.TRUE)
                .build();

        when(mockUserRepository.findByEmail(approvalSuccessful.getEmail()))
                .thenReturn(Optional.of(userDummy));

        when(mockTopUpApprovalRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(topUpApprovalDummy));

        responseSuccess = WalletResponse.builder()
                .user(userDummy)
                .message("Approval accepted, 50.0 IDR has been added to " + approvalSuccessful.getEmail())
                .build();

        when(mockUserRepository.findByEmail(approvalSuccessful.getEmail())).thenReturn(Optional.of(userDummy));

        WalletResponse result = service.approval(approvalSuccessful);
        Assertions.assertEquals(responseSuccess, result);
    }
}