package SelfSens.SelfSens.service;

import SelfSens.SelfSens.config.EnvironmentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class AiServiceFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(AiServiceFactory.class);
    
    private final EnvironmentConfig environmentConfig;
    private final Map<String, AiModelService> aiServices;
    @Autowired
     private RestTemplate restTemplate;
    @Autowired
    public AiServiceFactory(
            EnvironmentConfig environmentConfig,
            @Qualifier("geminiAiService") AiModelService geminiService,
            @Qualifier("mockAiService") AiModelService mockService) {
        
        this.environmentConfig = environmentConfig;
        this.aiServices = Map.of(
            "gemini", geminiService,
            "mock", mockService
        );
    }
    
    /**
     * Get the appropriate AI service based on configuration and availability
     * @return the selected AI service
     */
    public AiModelService getAiService() {
        String preferredProvider = environmentConfig.getAiProvider().toLowerCase();

        // Try to get the preferred provider
        AiModelService preferredService = aiServices.get(preferredProvider);
        if (preferredService != null && preferredService.isAvailable()) {
            logger.info("Using preferred AI service: {}", preferredService.getProviderName());
            return preferredService;
        }

        // Fallback to available services
        for (Map.Entry<String, AiModelService> entry : aiServices.entrySet()) {
            if (entry.getValue().isAvailable()) {
                logger.info("Using fallback AI service: {}", entry.getValue().getProviderName());
                return entry.getValue();
            }
        }

        // Last resort: return mock service
        logger.warn("No AI services available, using mock service");
        return aiServices.get(environmentConfig.getAiProvider());
//        return new GeminiAiService(environmentConfig , restTemplate);
    }
    
    /**
     * Get a specific AI service by name
     * @param serviceName the name of the service
     * @return the AI service or null if not found
     */
    public AiModelService getAiService(String serviceName) {
        return aiServices.get(serviceName.toLowerCase());
    }
    
    /**
     * Get all available AI services
     * @return list of available services
     */
    public List<AiModelService> getAvailableServices() {
        return aiServices.values().stream()
            .filter(AiModelService::isAvailable)
            .toList();
    }
    
    /**
     * Get the current active AI service info
     * @return information about the current service
     */
    public String getCurrentServiceInfo() {
        AiModelService currentService = getAiService();
        return String.format("Provider: %s, Model: %s, Available: %s", 
            currentService.getProviderName(),
            environmentConfig.getGeminiModel(),
            currentService.isAvailable());
    }
}
