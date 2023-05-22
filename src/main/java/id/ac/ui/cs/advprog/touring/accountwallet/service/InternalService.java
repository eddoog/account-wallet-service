package id.ac.ui.cs.advprog.touring.accountwallet.service;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.internal.UsersResponse;

public interface InternalService {
    UsersResponse fetchUserIds(Integer[] userIds);
}
