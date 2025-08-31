package SelfSens.SelfSens.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfileRequest {
    
    @NotBlank(message = "Profile ID is required")
    @Size(min = 1, max = 100, message = "Profile ID must be between 1 and 100 characters")
    private String profileId;
    
    @NotBlank(message = "Profile details are required")
    @Size(min = 1, max = 1000, message = "Profile details must be between 1 and 1000 characters")
    private String details;
    
    // Default constructor
    public ProfileRequest() {
    }
    
    // Parameterized constructor
    public ProfileRequest(String profileId, String details) {
        this.profileId = profileId;
        this.details = details;
    }
    
    // Getters and Setters
    public String getProfileId() {
        return profileId;
    }
    
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    // toString method
    @Override
    public String toString() {
        return "ProfileRequest{" +
                "profileId='" + profileId + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
