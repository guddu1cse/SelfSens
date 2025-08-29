package SelfSens.SelfSens.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentConfig {
    
    @Value("${GOOGLE_API_KEY:}")
    private String googleApiKey ;
    
    @Value("${GEMINI_MODEL:gemini-2.0-flash}")
    private String geminiModel;
    
    @Value("${GEMINI_TEMPERATURE:0.7}")
    private Double geminiTemperature;
    
    @Value("${GEMINI_MAX_TOKENS:1000}")
    private Integer geminiMaxTokens;
    
    @Value("${AI_PROVIDER:gemini}")
    private String aiProvider;

    @Value("${API_AUTH_TOKEN:}")
    private String authToken;
    
    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public String getAuthToken() { return authToken; }
    
    public String getGeminiModel() {
        return geminiModel;
    }
    
    public Double getGeminiTemperature() {
        return geminiTemperature;
    }
    
    public Integer getGeminiMaxTokens() {
        return geminiMaxTokens;
    }
    
    public String getAiProvider() {
        return aiProvider;
    }
    
    public boolean isGoogleApiKeyConfigured() {
        return googleApiKey != null && !googleApiKey.trim().isEmpty();
    }
}
