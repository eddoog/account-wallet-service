package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;

public class TokenGenerator implements Tool {
    private User user;

    public TokenGenerator(User user) {
        this.user = user;
    }

    @Override
    public String execute() {
        // TODO: Generate token for the user
        return null;
    }
}