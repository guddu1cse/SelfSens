package SelfSens.SelfSens.validation;

import SelfSens.SelfSens.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatRequestValidator {
    
    private static final int MAX_PROMPT_LENGTH = 1000;
    private static final int MIN_PROMPT_LENGTH = 1;
    
    /**
     * Validates the chat prompt request
     * @param prompt the prompt to validate
     * @throws ValidationException if validation fails
     */
    public void validatePrompt(String prompt) {
        Map<String, String> errors = new HashMap<>();
        
        if (!StringUtils.hasText(prompt)) {
            errors.put("prompt", "Prompt cannot be empty");
        } else if (prompt.length() < MIN_PROMPT_LENGTH) {
            errors.put("prompt", "Prompt must be at least " + MIN_PROMPT_LENGTH + " character long");
        } else if (prompt.length() > MAX_PROMPT_LENGTH) {
            errors.put("prompt", "Prompt cannot exceed " + MAX_PROMPT_LENGTH + " characters");
        }
        
        // Check for potentially harmful content
        if (StringUtils.hasText(prompt) && containsHarmfulContent(prompt)) {
            errors.put("prompt", "Prompt contains inappropriate content");
        }
        
        if (!errors.isEmpty()) {
            throw new ValidationException("Chat prompt validation failed", errors);
        }
    }
    
    /**
     * Checks if the prompt contains potentially harmful content
     * @param prompt the prompt to check
     * @return true if harmful content is detected
     */
    private boolean containsHarmfulContent(String prompt) {
        String lowerPrompt = prompt.toLowerCase();
        
        // Add your content filtering logic here
        String[] harmfulPatterns = {
            "hack", "exploit", "vulnerability", "ddos", "spam",
            "malware", "virus", "trojan", "phishing"
        };
        
        for (String pattern : harmfulPatterns) {
            if (lowerPrompt.contains(pattern)) {
                return true;
            }
        }
        
        return false;
    }
}
