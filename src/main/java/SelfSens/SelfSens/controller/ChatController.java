package SelfSens.SelfSens.controller;

import SelfSens.SelfSens.config.EnvironmentConfig;
import SelfSens.SelfSens.constants.ApplicationConstants;
import SelfSens.SelfSens.dto.ApiResponse;
import SelfSens.SelfSens.service.ChatService;
import SelfSens.SelfSens.util.RequestUtils;
import SelfSens.SelfSens.validation.ChatRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(ApplicationConstants.API_BASE_PATH + ApplicationConstants.CHAT_ENDPOINT)
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    
    private final ChatService chatService;
    private final ChatRequestValidator validator;
    @Autowired
    private EnvironmentConfig environmentConfig;

    public ChatController(ChatService chatService, ChatRequestValidator validator) {
        this.chatService = chatService;
        this.validator = validator;
    }

    @GetMapping(ApplicationConstants.PROMPT_ENDPOINT)
    public ResponseEntity<ApiResponse<String>> generate(
            @RequestParam(value = "prompt", defaultValue = ApplicationConstants.DEFAULT_PROMPT) String prompt,
            @RequestParam String authToken) {
        
        try {
            // Validate the request
            validator.validatePrompt(prompt);
            if (!authToken.equals(environmentConfig.getAuthToken())){
                logger.warn("{} Authentication failed: Invalid auth token provided", ApplicationConstants.LOG_PREFIX);
                ApiResponse<String> apiResponse = ApiResponse.error("Authentication failed: Invalid auth token", HttpStatus.UNAUTHORIZED);
                apiResponse.setPath(RequestUtils.getCurrentRequestPath());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
            }
            
            // Get the current request path
            String requestPath = RequestUtils.getCurrentRequestPath();
            
            logger.info("{} Processing chat prompt: {}", ApplicationConstants.LOG_PREFIX, prompt);
            
            // Generate response using service
            System.out.println("getting response... !");
            String response = chatService.generateResponse(prompt);
            System.out.println("result: "+ response);
            
            // Create success response
            ApiResponse<String> apiResponse = ApiResponse.success(ApplicationConstants.CHAT_SUCCESS_MESSAGE, response);
            apiResponse.setPath(requestPath);
            
            logger.info("{} Chat response generated successfully for prompt: {}", ApplicationConstants.LOG_PREFIX, prompt);
            
            return ResponseEntity.ok(apiResponse);
            
        } catch (Exception e) {
            logger.error("{} Error processing chat request: {}", ApplicationConstants.LOG_PREFIX, e.getMessage(), e);
            
            // Create error response
            ApiResponse<String> apiResponse = ApiResponse.error(
                ApplicationConstants.CHAT_ERROR_MESSAGE + ": " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
            apiResponse.setPath(RequestUtils.getCurrentRequestPath());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
    
    @GetMapping("/service-info")
    public ResponseEntity<ApiResponse<String>> getServiceInfo() {
        try {
            String requestPath = RequestUtils.getCurrentRequestPath();
            
            logger.info("{} Service info requested", ApplicationConstants.LOG_PREFIX);
            
            String serviceInfo = chatService.getServiceInfo();
            
            ApiResponse<String> apiResponse = ApiResponse.success("Service information retrieved successfully", serviceInfo);
            apiResponse.setPath(requestPath);
            
            return ResponseEntity.ok(apiResponse);
            
        } catch (Exception e) {
            logger.error("{} Error retrieving service info: {}", ApplicationConstants.LOG_PREFIX, e.getMessage(), e);
            
            ApiResponse<String> apiResponse = ApiResponse.error(
                "Failed to retrieve service information: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
            apiResponse.setPath(RequestUtils.getCurrentRequestPath());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
    
    @GetMapping("/available-services")
    public ResponseEntity<ApiResponse<String>> getAvailableServices() {
        try {
            String requestPath = RequestUtils.getCurrentRequestPath();
            
            logger.info("{} Available services requested", ApplicationConstants.LOG_PREFIX);
            
            String availableServices = chatService.getAvailableServices();
            
            ApiResponse<String> apiResponse = ApiResponse.success("Available services retrieved successfully", availableServices);
            apiResponse.setPath(requestPath);
            
            return ResponseEntity.ok(apiResponse);
            
        } catch (Exception e) {
            logger.error("{} Error retrieving available services: {}", ApplicationConstants.LOG_PREFIX, e.getMessage(), e);
            
            ApiResponse<String> apiResponse = ApiResponse.error(
                "Failed to retrieve available services: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
            apiResponse.setPath(RequestUtils.getCurrentRequestPath());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}