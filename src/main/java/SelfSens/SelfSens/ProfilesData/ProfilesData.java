package SelfSens.SelfSens.ProfilesData;

import SelfSens.SelfSens.entities.ProfileDetails;
import SelfSens.SelfSens.repo.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProfilesData class manages a global in-memory cache of profile details
 * This class provides fast access to profile data without hitting the database
 */
@Component
public class ProfilesData {
    
    private static final Logger logger = LoggerFactory.getLogger(ProfilesData.class);
    
    // Global map to store profileId -> ProfileDetails to avoid hitting the database every time
    private final Map<String, String> profileCache = new ConcurrentHashMap<>();
    
    @Autowired
    private ProfileRepository profileRepository;
    
    /**
     * Initialize the cache with data from database on application startup
     */
    @PostConstruct
    public void initializeCache() {
        logger.info("Initializing profile cache...");
        loadLatestData();
        logger.info("Profile cache initialized with {} profiles", profileCache.size());
    }
    
    /**
     * Load latest data from database and update the cache
     * This method is called by the scheduler every hour
     */
    public void loadLatestData() {
        try {
            logger.info("Loading latest profile data from database...");
            
            // Fetch all profiles from database
            List<ProfileDetails> profiles = profileRepository.findAll();
            
            // Clear existing cache
            profileCache.clear();
            
            // Populate cache with latest data
            for (ProfileDetails profile : profiles) {
                profileCache.put(profile.getProfileId(), profile.getDetails());
            }

            System.out.println("Profiles loaded into cache: " + profileCache);
            
            logger.info("Successfully loaded {} profiles into cache", profileCache.size());
            
        } catch (Exception e) {
            logger.error("Error loading profile data from database: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Get profile details by profile ID from cache
     * @param profileId the profile ID to lookup
     * @return ProfileDetails if found, null otherwise
     */
    public String getProfileById(String profileId) {
        return profileCache.get(profileId);
    }
    
    /**
     * Check if a profile exists in cache
     * @param profileId the profile ID to check
     * @return true if profile exists, false otherwise
     */
    public boolean profileExists(String profileId) {
        return profileCache.containsKey(profileId);
    }
    
    /**
     * Get all profiles from cache
     * @return List of all ProfileDetails
     */
    public List<String> getAllProfiles() {
        return List.copyOf(profileCache.values());
    }
    
    /**
     * Get the current cache size
     * @return number of profiles in cache
     */
    public int getCacheSize() {
        return profileCache.size();
    }
    
    /**
     * Clear the entire cache
     */
    public void clearCache() {
        profileCache.clear();
        logger.info("Profile cache cleared");
    }
    
    /**
     * Reset profiles by clearing cache and reloading from database
     * This method is called by the scheduler to refresh data
     */
    public void resetProfiles() {
        logger.info("Resetting profiles data...");
        clearCache();
        loadLatestData();
        logger.info("Profiles data reset completed");
    }
    
}
