package SelfSens.SelfSens.service;

import SelfSens.SelfSens.entities.ProfileDetails;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    
    /**
     * Create a new profile
     */
    ProfileDetails createProfile(ProfileDetails profile);
    
    /**
     * Get profile by ID
     */
    Optional<ProfileDetails> getProfileById(String profileId);
    
    /**
     * Get all profiles
     */
    List<ProfileDetails> getAllProfiles();
    
    /**
     * Update profile details
     */
    ProfileDetails updateProfile(String profileId, ProfileDetails profileDetails);
    
    /**
     * Delete profile by ID
     */
    boolean deleteProfile(String profileId);
    
    /**
     * Search profiles by details
     */
    List<ProfileDetails> searchProfilesByDetails(String searchTerm);
    
    /**
     * Check if profile exists
     */
    boolean profileExists(String profileId);
    
    /**
     * Save or update profile
     */
    ProfileDetails saveProfile(ProfileDetails profile);

}
