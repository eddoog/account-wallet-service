package id.ac.ui.cs.advprog.touring.accountwallet.controller;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.ProfileRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.ProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditProfileResponse;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.service.EditProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/editProfile")
@RequiredArgsConstructor
public class EditProfileController {

    private final EditProfileService editProfileService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(@RequestBody ProfileRequest request) {
        ProfileResponse response = editProfileService.getProfile(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/personalData")
    @CrossOrigin(maxAge = 3600)
    public ResponseEntity<EditProfileResponse> editPersonalData(@RequestBody EditPersonalDataRequest request) {
        EditProfileResponse response = editProfileService.editPersonalData(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/username")
    public ResponseEntity<EditProfileResponse> editUsername(@RequestBody EditUsernameRequest request) {
        EditProfileResponse response = editProfileService.editUsername(request);
        return ResponseEntity.ok(response);
    }
}
