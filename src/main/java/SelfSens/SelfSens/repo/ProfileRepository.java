package SelfSens.SelfSens.repo;

import SelfSens.SelfSens.entities.ProfileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileDetails, String> {
    
    
    Optional<ProfileDetails> findByProfileId(String profileId);
    
    
    List<ProfileDetails> findByDetailsContainingIgnoreCase(String details);
    
    /**
     * Check if profile exists by profile ID
     */
    boolean existsByProfileId(String profileId);
    
    /**
     * Delete profile by profile ID
     */
    void deleteByProfileId(String profileId);
}
