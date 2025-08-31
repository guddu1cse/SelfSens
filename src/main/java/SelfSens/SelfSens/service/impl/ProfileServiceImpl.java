package SelfSens.SelfSens.service.impl;

import SelfSens.SelfSens.entities.ProfileDetails;
import SelfSens.SelfSens.repo.ProfileRepository;
import SelfSens.SelfSens.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
    
    private final ProfileRepository profileRepository;
    
    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
    @Override
    public ProfileDetails createProfile(ProfileDetails profile) {
        if (profile.getProfileId() == null || profile.getProfileId().trim().isEmpty()) {
            throw new IllegalArgumentException("Profile ID cannot be null or empty");
        }
        
        if (profileRepository.existsByProfileId(profile.getProfileId())) {
            throw new IllegalArgumentException("Profile with ID " + profile.getProfileId() + " already exists");
        }
        
        return profileRepository.save(profile);
    }
    
    @Override
    public Optional<ProfileDetails> getProfileById(String profileId) {
        if (profileId == null || profileId.trim().isEmpty()) {
            throw new IllegalArgumentException("Profile ID cannot be null or empty");
        }
        return profileRepository.findByProfileId(profileId);
    }
    
    @Override
    public List<ProfileDetails> getAllProfiles() {
        return profileRepository.findAll();
    }
    
    @Override
    public ProfileDetails updateProfile(String profileId, ProfileDetails profileDetails) {
        if (profileId == null || profileId.trim().isEmpty()) {
            throw new IllegalArgumentException("Profile ID cannot be null or empty");
        }
        
        Optional<ProfileDetails> existingProfile = profileRepository.findByProfileId(profileId);
        if (existingProfile.isEmpty()) {
            throw new IllegalArgumentException("Profile with ID " + profileId + " not found");
        }
        
        ProfileDetails profileToUpdate = existingProfile.get();
        profileToUpdate.setDetails(profileDetails.getDetails());
        
        return profileRepository.save(profileToUpdate);
    }
    
    @Override
    public boolean deleteProfile(String profileId) {
        if (profileId == null || profileId.trim().isEmpty()) {
            throw new IllegalArgumentException("Profile ID cannot be null or empty");
        }
        
        if (!profileRepository.existsByProfileId(profileId)) {
            return false;
        }
        
        profileRepository.deleteByProfileId(profileId);
        return true;
    }
    
    @Override
    public List<ProfileDetails> searchProfilesByDetails(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllProfiles();
        }
        return profileRepository.findByDetailsContainingIgnoreCase(searchTerm.trim());
    }
    
    @Override
    public boolean profileExists(String profileId) {
        if (profileId == null || profileId.trim().isEmpty()) {
            return false;
        }
        return profileRepository.existsByProfileId(profileId);
    }
    
    @Override
    public ProfileDetails saveProfile(ProfileDetails profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile cannot be null");
        }
        return profileRepository.save(profile);
    }
}
