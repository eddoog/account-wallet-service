package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;

public class CustomerBuilder extends UserBuilder {
    @Override
    protected RegisterResponse createUser(RegisterBuilderRequest request, User user) {

        user.setVerificationCode(null);
        user.setIsEnabled(true);

        return RegisterResponse
                .builder()
                .user(user)
                .message("New Customer has been successfully made")
                .build();
    }
}
