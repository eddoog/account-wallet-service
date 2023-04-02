package id.ac.ui.cs.advprog.touring.accountwallet.core.utils;

import id.ac.ui.cs.advprog.touring.accountwallet.model.User;

public class EmailSender implements Tool {
    private User user;
    private String siteURL;
    public EmailSender(User user, String siteURL) {
        this.user = user;
        this.siteURL = siteURL;
    }

    @Override
    public String execute() {
        // TODO: Send Email Message
        return null;
    }
}
