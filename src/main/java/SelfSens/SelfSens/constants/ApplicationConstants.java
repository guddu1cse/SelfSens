package SelfSens.SelfSens.constants;

public final class ApplicationConstants {
    
    // API Endpoints
    public static final String API_BASE_PATH = "/api";
    public static final String CHAT_ENDPOINT = "/chat";
    public static final String HEALTH_ENDPOINT = "/health";
    public static final String PROMPT_ENDPOINT = "/prompt";
    
    // Service Information
    public static final String SERVICE_NAME = "SelfSens Chat API";
    public static final String SERVICE_VERSION = "1.0.0";
    
    // Default Values
    public static final String DEFAULT_PROMPT = "Tell me about yourself ?";
    
    // Error Messages
    public static final String GENERIC_ERROR_MESSAGE = "An unexpected error occurred";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation failed";
    public static final String CHAT_ERROR_MESSAGE = "Failed to generate chat response";
    public static final String HEALTH_CHECK_SUCCESS = "Health check completed successfully";
    public static final String CHAT_SUCCESS_MESSAGE = "Chat response generated successfully";
    
    // HTTP Status Messages
    public static final String BAD_REQUEST_MESSAGE = "Bad Request";
    public static final String NOT_FOUND_MESSAGE = "Not Found";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
    
    // Logging
    public static final String LOG_PREFIX = "[SelfSens]";
    
    // Prevent instantiation
    private ApplicationConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
