package SelfSens.SelfSens.config;

import SelfSens.SelfSens.entities.ProfileDetails;
import SelfSens.SelfSens.repo.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initData(ProfileRepository repository) {
        return args -> {
            String profileId = "guddu1cse";
            if (!repository.existsById(profileId)) {
                logger.info("Initializing fake data for user: {}", profileId);
                
                String details = "Name: Guddu (guddu1cse)\n" +
                        "Role: Full Stack Developer and AI Enthusiast\n" +
                        "Skills: Java, Spring Boot, React, AI Integration, Cloud Architecture\n" +
                        "Projects: SelfSens AI, Tuckit Locker System\n" +
                        "Bio: Guddu is a passionate developer who loves building agentic AI solutions. " +
                        "He has extensive experience in building scalable backend systems and beautiful frontend interfaces. " +
                        "He is currently working on SelfSens, a chatbot integrated into his portfolio to showcase his skills and experience.";
                
                ProfileDetails profile = new ProfileDetails(profileId, details);
                repository.save(profile);
                
                logger.info("Fake data initialized successfully for {}", profileId);
            } else {
                logger.info("Profile for {} already exists, skipping initialization.", profileId);
            }
        };
    }
}
