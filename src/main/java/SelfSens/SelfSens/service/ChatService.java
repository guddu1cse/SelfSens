package SelfSens.SelfSens.service;

import SelfSens.SelfSens.exception.ChatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    
    private final AiServiceFactory aiServiceFactory;
    
    @Autowired
    public ChatService(AiServiceFactory aiServiceFactory) {
        this.aiServiceFactory = aiServiceFactory;
    }
    
    public String generateResponse(String prompt) {
        try {
            // Get the appropriate AI service
            AiModelService aiService = aiServiceFactory.getAiService();
            
            logger.info("Using AI service: {} for prompt: {}", 
                aiService.getProviderName(), prompt);
            
            // Generate response using the selected AI service
            String response = aiService.generateResponse(prompt);
            
            logger.info("Response generated successfully using: {}", 
                aiService.getProviderName());
            
            return response;
            
        } catch (Exception e) {
            logger.error("Error generating AI response: {}", e.getMessage(), e);
            throw new ChatException("Failed to generate AI response: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get information about the current AI service
     * @return service information
     */
    public String getServiceInfo() {
        return aiServiceFactory.getCurrentServiceInfo();
    }
    
    /**
     * Get available AI services
     * @return list of available services
     */
    public String getAvailableServices() {
        java.util.List<AiModelService> services = aiServiceFactory.getAvailableServices();
        StringBuilder info = new StringBuilder("Available AI Services:\n");
        
        for (AiModelService service : services) {
            info.append("- ").append(service.getProviderName())
                .append(" (Available: ").append(service.isAvailable()).append(")\n");
        }
        
        return info.toString();
    }
}
