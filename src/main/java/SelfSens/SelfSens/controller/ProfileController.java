package SelfSens.SelfSens.controller;

import SelfSens.SelfSens.dto.ApiResponse;
import SelfSens.SelfSens.dto.ProfileRequest;
import SelfSens.SelfSens.entities.ProfileDetails;
import SelfSens.SelfSens.ProfilesData.ProfilesData;
import SelfSens.SelfSens.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "*")
public class ProfileController {
    
    private final ProfileService profileService;
    
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    /**
     * Create a new profile
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProfileDetails>> createProfile(@Valid @RequestBody ProfileRequest request) {
        try {
            ProfileDetails profile = new ProfileDetails(request.getProfileId(), request.getDetails());
            ProfileDetails createdProfile = profileService.createProfile(profile);

            
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Profile Created", createdProfile, 201));
        } catch (IllegalArgumentException e) {
            ApiResponse<ProfileDetails> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null,
                400
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
    /**
     * Get profile by ID
     */
    @GetMapping("/{profileId}")
    public ResponseEntity<ApiResponse<ProfileDetails>> getProfileById(@PathVariable String profileId) {
        try {
            return profileService.getProfileById(profileId)
                .map(profile -> {
                    ApiResponse<ProfileDetails> response = new ApiResponse<>(
                        true, 
                        "Profile retrieved successfully", 
                        profile,
                        200
                    );
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<ProfileDetails> response = new ApiResponse<>(
                        false, 
                        "Profile not found", 
                        null,
                        404
                    );
                    return ResponseEntity.notFound().build();
                });
        } catch (IllegalArgumentException e) {
            ApiResponse<ProfileDetails> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null,
                400
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Get all profiles
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProfileDetails>>> getAllProfiles() {
        List<ProfileDetails> profiles = profileService.getAllProfiles();
        
        ApiResponse<List<ProfileDetails>> response = new ApiResponse<>(
            true, 
            "Profiles retrieved successfully", 
            profiles,
            200
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Update profile
     */
    @PutMapping("/{profileId}")
    public ResponseEntity<ApiResponse<ProfileDetails>> updateProfile(
            @PathVariable String profileId,
            @Valid @RequestBody ProfileRequest request) {
        try {
            ProfileDetails profileDetails = new ProfileDetails(request.getProfileId(), request.getDetails());
            ProfileDetails updatedProfile = profileService.updateProfile(profileId, profileDetails);
            
            ApiResponse<ProfileDetails> response = new ApiResponse<>(
                true, 
                "Profile updated successfully", 
                updatedProfile,
                200
            );
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<ProfileDetails> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null,
                400
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Delete profile
     */
    @DeleteMapping("/{profileId}")
    public ResponseEntity<ApiResponse<String>> deleteProfile(@PathVariable String profileId) {
        try {
            boolean deleted = profileService.deleteProfile(profileId);
            
            if (deleted) {
                ApiResponse<String> response = new ApiResponse<>(
                    true, 
                    "Profile deleted successfully", 
                    "Profile with ID " + profileId + " has been deleted",
                    200
                );
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<String> response = new ApiResponse<>(
                    false, 
                    "Profile not found", 
                    null,
                    404
                );
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>(
                false, 
                e.getMessage(), 
                null,
                400
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Search profiles by details
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProfileDetails>>> searchProfiles(
            @RequestParam(required = false) String q) {
        List<ProfileDetails> profiles = profileService.searchProfilesByDetails(q);
        
        ApiResponse<List<ProfileDetails>> response = new ApiResponse<>(
            true, 
            "Profiles search completed", 
            profiles,
            200
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Check if profile exists
     */
    @GetMapping("/{profileId}/exists")
    public ResponseEntity<ApiResponse<Boolean>> checkProfileExists(@PathVariable String profileId) {
        try {
            boolean exists = profileService.profileExists(profileId);
            
            ApiResponse<Boolean> response = new ApiResponse<>(
                true, 
                "Profile existence check completed", 
                exists,
                200
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Boolean> response = new ApiResponse<>(
                false, 
                "Error checking profile existence", 
                null,
                500
            );
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
