package SelfSens.SelfSens.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import SelfSens.SelfSens.ProfilesData.ProfilesData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class ProfileDataRefreshScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(ProfileDataRefreshScheduler.class);

    @Autowired
    private ProfilesData profilesData;
    
    @Scheduled(fixedRate = 3600000)
    public void resetProfileDataFixedRate() {
        try {
            logger.info("Starting scheduled profile data reset (fixed rate)...");
            
            // Reset the profiles data
            profilesData.resetProfiles();
            
            logger.info("Profile data reset completed successfully");
            
            // Get current time in IST
            LocalDateTime istTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = istTime.format(formatter);
            
            System.out.println("Profile data refresh completed at IST: " + timestamp);
        } catch (Exception e) {
            logger.error("Error occurred during scheduled profile data reset", e);
        }
    }
}