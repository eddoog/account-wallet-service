package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.EditPersonalDataResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.EditUsernameResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.service.EditProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/editProfile")
@RequiredArgsConstructor
public class EditProfileController {

    private final EditProfileService editProfileService;
    @PutMapping("/update/personalData/{id}")
    public ResponseEntity<EditPersonalDataResponse> editPersonalData(@PathVariable Integer id, @RequestBody EditPersonalDataRequest request) {
        EditPersonalDataResponse response = editProfileService.editPersonalData(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/username/{id}")
    public ResponseEntity<EditUsernameResponse> editUsername(@PathVariable Integer id, @RequestBody EditUsernameRequest request) {
        EditUsernameResponse response = editProfileService.editUsername(id, request);
        return ResponseEntity.ok(response);
    }
}
