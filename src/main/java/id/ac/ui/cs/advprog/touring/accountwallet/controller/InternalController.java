package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.internal.UsersRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.internal.UsersResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.service.InternalService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
public class InternalController {
    private final InternalService internalService;

    @PostMapping("/fetch-uids")
    public ResponseEntity<UsersResponse> fetchUids(
            @RequestBody UsersRequest users) {
        return ResponseEntity.ok(internalService.fetchUserIds(users.getUserIds()));
    }
}
