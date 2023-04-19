package id.ac.ui.cs.advprog.touring.accountwallet.core.builder;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.RegisterResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.builder.RegisterBuilderRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

public abstract class UserBuilder {
    protected abstract RegisterResponse createUser(RegisterBuilderRequest request, User user) throws MessagingException, UnsupportedEncodingException;
    public RegisterResponse buildUser(RegisterBuilderRequest request) throws MessagingException, UnsupportedEncodingException {

        User newUser = decideUser(request);

        return createUser(request, newUser);
    }

    private User setDefinedUser(User user, RegisterBuilderRequest request) {
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    private User createNewUser(RegisterBuilderRequest request) {
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        return newUser;
    }

    private User decideUser(RegisterBuilderRequest request) {
        User newUser;

        Optional<User> optionalUser = request.getUserIfAlreadyBeenMade();
        newUser = optionalUser.map(user -> setDefinedUser(user, request)).orElseGet(() -> createNewUser(request));

        return newUser;
    }

}
