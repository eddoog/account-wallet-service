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
import id.ac.ui.cs.advprog.touring.accountwallet.exception.wallet.*;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.repository.UserRepository;

import id.ac.ui.cs.advprog.touring.accountwallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final UserRepository userRepository;
    CurrencyConverter currencyStrategy;
    Integer amountConverted;
    Integer approvalListIndex = 0;
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

        getTopUpHistory().add(WalletTransferRequest.builder()
                                                .email(request.getEmail())
                                                .amount(amountConverted)
                                                .build());

        getApprovalList().put(approvalListIndex, WalletApprovalRequest.builder()
                                        .email(request.getEmail())
                                        .amount(amountConverted)
                                        .approval(null)
                                        .index(approvalListIndex++)
                                        .build());

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
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(email);
        }

        User user = userOptional.get();

        if (request.getAmount() < 0) {
            throw new NegativeAmountException();
        }
        if (user.getWalletAmount() < request.getAmount()) {
            throw new InsufficientFundsException();
        }

        user.setWalletAmount(user.getWalletAmount() - request.getAmount());

        getTopUpHistory().add(WalletTransferRequest.builder()
                .email(request.getEmail())
                .amount(request.getAmount() * -1)
                .build());

        userRepository.save(user);

        return WalletResponse.builder()
                .user(user)
                .message("Transaction successful, " + amountConverted + " IDR has been deducted")
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

        if (!(getApprovalList().containsKey(request.getIndex()))) {
            throw new IndexNotFoundException();
        }
        WalletApprovalRequest indexedWalletApprovalRequest = getApprovalList().get(request.getIndex());

        if ((!(indexedWalletApprovalRequest.getEmail().equalsIgnoreCase(request.getEmail()))) ||
                (!Objects.equals(indexedWalletApprovalRequest.getAmount(), request.getAmount()))) {
            throw new IncorrectDataException();
        }

        if (Boolean.FALSE.equals(request.getApproval())) {
            throw new ApprovalRejectedException();
        }

        user.setWalletAmount(user.getWalletAmount() + request.getAmount());
        userRepository.save(user);

        getApprovalList().remove(request.getIndex());

        return WalletResponse.builder()
                .user(user)
                .message("Approval accepted, "
                        + request.getAmount() + " IDR has been added to " + request.getEmail())
                .build();
    }

    public List<WalletTransferRequest> getTopUpHistory() {
        return walletRepository.getTopUpHistory();
    }

    public Map<Integer, WalletApprovalRequest> getApprovalList() {
        return walletRepository.getApprovalList();
    }
}