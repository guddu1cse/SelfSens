package SelfSens.SelfSens.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("mockAiService")
public class MockAiService implements AiModelService {
    
    private static final Logger logger = LoggerFactory.getLogger(MockAiService.class);
    
    @Override
    public String generateResponse(String prompt) throws Exception {
        logger.info("Using mock AI service for prompt: {}", prompt);
        
        // Mock implementation with some intelligence
        if (prompt.toLowerCase().contains("joke")) {
            return "Here's a joke about Spring Boot: Why did the Spring Boot developer go broke? Because he used too many @Autowired annotations!";
        } else if (prompt.toLowerCase().contains("hello") || prompt.toLowerCase().contains("hi")) {
            return "Hello! I'm SelfSens, your AI assistant. How can I help you today?";
        } else if (prompt.toLowerCase().contains("help")) {
            return "I can help you with various topics. Try asking me a question or requesting a joke!";
        } else if (prompt.toLowerCase().contains("java")) {
            return "Java is a high-level, class-based, object-oriented programming language. It's designed to have as few implementation dependencies as possible. Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM) regardless of the underlying computer architecture.";
        } else if (prompt.toLowerCase().contains("spring")) {
            return "Spring Framework is an application framework and inversion of control container for the Java platform. It provides a comprehensive programming and configuration model for modern Java-based enterprise applications.";
        } else {
            return "I understand you said: '" + prompt + "'. This is a mock response. Please configure actual AI integration to get real responses.";
        }
    }
    
    @Override
    public String getProviderName() {
        return "Mock AI Service";
    }
    
    @Override
    public boolean isAvailable() {
        return true; // Mock service is always available
    }
}
