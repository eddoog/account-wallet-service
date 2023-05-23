package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.wallet.CurrencyConverter;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.wallet.EuroCurrencyConverter;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.wallet.IDRCurrencyConverter;
import id.ac.ui.cs.advprog.touring.accountwallet.core.utils.wallet.USDCurrencyConverter;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletHistoryResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTransferRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet.*;
import id.ac.ui.cs.advprog.touring.accountwallet.model.TopUpApproval;
import id.ac.ui.cs.advprog.touring.accountwallet.model.Transaction;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.TopUpApprovalRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.TransactionRepository;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final TopUpApprovalRepository topUpApprovalRepository;
    CurrencyConverter currencyStrategy;
    Integer amountConverted;

    @Override
    public WalletResponse topUp(WalletTopUpRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(email);
        }

        var user = userOptional.get();

        switch (request.getCurrencyType().toLowerCase()) {
            case "euro" -> {
                currencyStrategy = new EuroCurrencyConverter();
                amountConverted = currencyStrategy.getConvertedCurrency(request.getAmount());
            }
            case "united states dollar" -> {
                currencyStrategy = new USDCurrencyConverter();
                amountConverted = currencyStrategy.getConvertedCurrency(request.getAmount());
            }
            case "indonesian rupiah" -> {
                currencyStrategy = new IDRCurrencyConverter();
                amountConverted = currencyStrategy.getConvertedCurrency(request.getAmount());
            }
            default ->
                throw new CurrencyNotSupportedException(request.getCurrencyType());
        }

        var topUpApproval = TopUpApproval.builder().user(user).transactionAmount(amountConverted).build();
        topUpApprovalRepository.save(topUpApproval);

        userRepository.save(user);

        return WalletResponse.builder()
                .user(user)
                .message("Top up request of " + amountConverted + " IDR successful, waiting for approval")
                .build();
    }

    @Override
    public WalletResponse transfer(WalletTransferRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        if (request.getAmount() == null) {
            throw new AmountNullException();
        }

        var user = userOptional.get();

        if (request.getAmount() < 0) {
            throw new AmountNegativeException();
        }
        if (user.getWalletAmount() < request.getAmount()) {
            throw new InsufficientFundsException();
        }

        user.setWalletAmount(user.getWalletAmount() - request.getAmount());

        var transaction = Transaction.builder().user(user).transactionAmount(request.getAmount() * -1.0)
                .message("Transfer successful").build();
        transactionRepository.save(transaction);

        userRepository.save(user);

        return WalletResponse.builder()
                .user(user)
                .message("Transaction successful, " + request.getAmount() + " IDR has been deducted")
                .build();
    }

    @Override
    public WalletResponse approval(WalletApprovalRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(email);
        }

        var user = userOptional.get();
        var topUpApprovalOpt = topUpApprovalRepository.findById(request.getTransactionId());
        if (!topUpApprovalOpt.isPresent()) {
            throw new IncorrectDataException();
        }

        var topUpApproval = topUpApprovalOpt.get();

        user.setWalletAmount(user.getWalletAmount() + topUpApproval.getTransactionAmount());
        userRepository.save(user);

        var approvedTransaction = Transaction.builder()
                .user(user)
                .transactionAmount(topUpApproval.getTransactionAmount())
                .message("Top up approved")
                .build();
        transactionRepository.save(approvedTransaction);

        topUpApprovalRepository.deleteById(topUpApproval.getId());

        return WalletResponse.builder()
                .user(user)
                .message("Approval accepted, "
                        + topUpApproval.getTransactionAmount() + " IDR has been added to " + request.getEmail())
                .build();

    }

    @Override
    public WalletHistoryResponse history(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(email);
        }

        var user = userOptional.get();
        var topUps = topUpApprovalRepository.findAllByUser(user);
        var transactions = transactionRepository.findAllByUser(user);

        return WalletHistoryResponse.builder().pendingApprovals(topUps).transactions(transactions).build();

    }
}