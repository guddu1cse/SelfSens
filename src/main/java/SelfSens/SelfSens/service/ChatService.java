package SelfSens.SelfSens.service;

import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    public String generateResponse(String prompt) {
        // Mock implementation for now - replace with actual AI integration later
        if (prompt.toLowerCase().contains("joke")) {
            return "Here's a joke about Spring Boot: Why did the Spring Boot developer go broke? Because he used too many @Autowired annotations!";
        } else if (prompt.toLowerCase().contains("hello") || prompt.toLowerCase().contains("hi")) {
            return "Hello! I'm SelfSens, your AI assistant. How can I help you today?";
        } else if (prompt.toLowerCase().contains("help")) {
            return "I can help you with various topics. Try asking me a question or requesting a joke!";
        } else {
            return "I understand you said: '" + prompt + "'. This is a mock response. Please configure actual AI integration to get real responses.";
        }
    }
}
