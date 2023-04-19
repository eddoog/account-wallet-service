package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.service.EditProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/editProfile")
@RequiredArgsConstructor
public class EditProfileController {

    private final EditProfileService editProfileService;
    @PutMapping("/update/personalData/{id}")
    public ResponseEntity<EditProfileResponse> editPersonalData(@RequestBody EditPersonalDataRequest request) {
        EditProfileResponse response = editProfileService.editPersonalData(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/username/{id}")
    public ResponseEntity<EditProfileResponse> editUsername(@RequestBody EditUsernameRequest request) {
        EditProfileResponse response = editProfileService.editUsername(request);
        return ResponseEntity.ok(response);
    }
}
