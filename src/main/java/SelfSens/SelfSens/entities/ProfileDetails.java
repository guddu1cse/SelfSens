package SelfSens.SelfSens.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "profile_details")
public class ProfileDetails {
    
    @Id
    private String profileId;
    
    @Column(name = "details", length = 99999999)
    private String details;
    
    // Default constructor
    public ProfileDetails() {
    }
    
    // Parameterized constructor
    public ProfileDetails(String profileId, String details) {
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
        return "ProfileDetails{" +
                "profileId='" + profileId + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
