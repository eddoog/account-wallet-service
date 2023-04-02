package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import id.ac.ui.cs.advprog.touring.accountwallet.model.UserType;

public abstract class UserBuilder {
    protected abstract RegisterResponse createUser(RegisterBuilderRequest request);

    public RegisterResponse getUser(RegisterBuilderRequest request) {
        return createUser(request);
    }
}
