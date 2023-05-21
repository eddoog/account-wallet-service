package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.core.wallet.CurrencyConverter;
import id.ac.ui.cs.advprog.touring.accountwallet.core.wallet.EuroCurrencyConverter;
import id.ac.ui.cs.advprog.touring.accountwallet.core.wallet.IDRCurrencyConverter;
import id.ac.ui.cs.advprog.touring.accountwallet.core.wallet.USDCurrencyConverter;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletApprovalRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTopUpRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.wallet.WalletTransferRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.login.UserNotFoundException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet.CurrencyNotSupportedException;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;

import id.ac.ui.cs.advprog.touring.accountwallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final UserRepository userRepository;
    CurrencyConverter currencyStrategy;
    Integer amountConverted;
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public WalletResponse topUp(WalletTopUpRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }

        User user = userOptional.get();

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

        user.setWalletAmount(user.getWalletAmount() + amountConverted);

        userRepository.save(user);

        return WalletResponse.builder()
                .user(user)
                .message("Top up of " + amountConverted + " IDR successful")
                .build();
    }

    @Override
    public WalletResponse transfer(WalletTransferRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }

        User user = userOptional.get();

        if (user.getWalletAmount() < request.getAmount()) {
            return WalletResponse.builder()
                    .user(user)
                    .message("Insufficient funds")
                    .build();
        }
        user.setWalletAmount(user.getWalletAmount() - request.getAmount());
        return WalletResponse.builder()
                .user(user)
                .message("Transaction successful, " + amountConverted + " has been deducted")
                .build();
    }

    @Override
    public WalletResponse approval(WalletApprovalRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }

        User user = userOptional.get();

        if (request.getApproval()) {
            user.setWalletAmount(user.getWalletAmount() + request.getAmount());
            return WalletResponse.builder()
                    .user(user)
                    .message("Approval accepted, "
                                + request.getAmount() + " IDR has been added to " + request.getEmail())
                    .build();
        }
        return WalletResponse.builder()
                .user(user)
                .message("Approval rejected")
                .build();
    }

    public List<WalletTopUpRequest> getTopUpHistory() {
        return walletRepository.getTopUpHistory();
    }

    public List<WalletApprovalRequest> getApprovalList() {
        return walletRepository.getApprovalList();
    }
}