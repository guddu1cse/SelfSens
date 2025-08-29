package SelfSens.SelfSens.service;

public interface AiModelService {
    
    /**
     * Generate a response using the AI model
     * @param prompt the input prompt
     * @return the AI-generated response
     * @throws Exception if there's an error generating the response
     */
    String generateResponse(String prompt) throws Exception;
    
    /**
     * Get the name of the AI model provider
     * @return the provider name
     */
    String getProviderName();
    
    /**
     * Check if the AI model is available and configured
     * @return true if the model is available
     */
    boolean isAvailable();
}
