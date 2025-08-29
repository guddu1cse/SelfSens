package SelfSens.SelfSens.service;


import SelfSens.SelfSens.config.EnvironmentConfig;
import SelfSens.SelfSens.exception.ChatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("geminiAiService")
public class GeminiAiService implements AiModelService {
    
    private static final Logger logger = LoggerFactory.getLogger(GeminiAiService.class);
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    
    private final EnvironmentConfig environmentConfig;
    private final RestTemplate restTemplate;
    
    @Autowired
    public GeminiAiService(EnvironmentConfig environmentConfig, RestTemplate restTemplate) {
        this.environmentConfig = environmentConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public String generateResponse(String prompt) throws Exception {
        try {
            logger.info("Generating response using Gemini model: {}", environmentConfig.getGeminiModel());
            
            // Prepare the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", environmentConfig.getGoogleApiKey());
            
            // Prepare the request body
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            content.put("parts", new Object[]{
                Map.of("text", prompt)
            });
            requestBody.put("contents", new Object[]{content});
            
            // Set generation config
            Map<String, Object> generationConfig = new HashMap<>();
            generationConfig.put("temperature", environmentConfig.getGeminiTemperature());
            generationConfig.put("maxOutputTokens", environmentConfig.getGeminiMaxTokens());
            requestBody.put("generationConfig", generationConfig);
            
            // Make the API call
            System.out.println("Request send to gemini" + new Date().getTime());
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                GEMINI_API_URL,
                HttpMethod.POST,
                request,
                Map.class,
                environmentConfig.getGeminiModel()
            );
            System.out.println("Responsed from Gemini" + new Date().getTime());
            
            // Extract the response text with safe casting
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("candidates")) {
                Object candidatesObj = responseBody.get("candidates");
                if (candidatesObj instanceof java.util.List) {
                    java.util.List<?> candidatesList = (java.util.List<?>) candidatesObj;
                    if (!candidatesList.isEmpty()) {
                        Object candidateObj = candidatesList.get(0);
                        if (candidateObj instanceof Map) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> candidate = (Map<String, Object>) candidateObj;
                            Object contentObj = candidate.get("content");
                            if (contentObj instanceof Map) {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> contentResponse = (Map<String, Object>) contentObj;
                                Object partsObj = contentResponse.get("parts");
                                if (partsObj instanceof java.util.List) {
                                    java.util.List<?> partsList = (java.util.List<?>) partsObj;
                                    if (!partsList.isEmpty()) {
                                        Object partObj = partsList.get(0);
                                        if (partObj instanceof Map) {
                                            @SuppressWarnings("unchecked")
                                            Map<String, Object> part = (Map<String, Object>) partObj;
                                            Object textObj = part.get("text");
                                            if (textObj instanceof String) {
                                                String aiResponse = (String) textObj;
                                                logger.info("Gemini response generated successfully");
                                                return aiResponse;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            throw new ChatException("Invalid response format from Gemini API");
            
        } catch (Exception e) {
            logger.error("Error generating Gemini response: {}", e.getMessage(), e);
            System.out.println(e.getMessage());
            throw new ChatException("Failed to generate response using Gemini: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String getProviderName() {
        return "Google Gemini";
    }
    
    @Override
    public boolean isAvailable() {
        return environmentConfig.isGoogleApiKeyConfigured();
    }
}
