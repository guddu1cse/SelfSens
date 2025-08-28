package SelfSens.SelfSens.controller;

import SelfSens.SelfSens.constants.ApplicationConstants;
import SelfSens.SelfSens.dto.ApiResponse;
import SelfSens.SelfSens.service.ChatService;
import SelfSens.SelfSens.util.RequestUtils;
import SelfSens.SelfSens.validation.ChatRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApplicationConstants.API_BASE_PATH + ApplicationConstants.CHAT_ENDPOINT)
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    
    private final ChatService chatService;
    private final ChatRequestValidator validator;

    public ChatController(ChatService chatService, ChatRequestValidator validator) {
        this.chatService = chatService;
        this.validator = validator;
    }

    @GetMapping(ApplicationConstants.PROMPT_ENDPOINT)
    public ResponseEntity<ApiResponse<String>> generate(
            @RequestParam(value = "prompt", defaultValue = ApplicationConstants.DEFAULT_PROMPT) String prompt) {
        
        try {
            // Validate the request
            validator.validatePrompt(prompt);
            
            // Get the current request path
            String requestPath = RequestUtils.getCurrentRequestPath();
            
            logger.info("{} Processing chat prompt: {}", ApplicationConstants.LOG_PREFIX, prompt);
            
            // Generate response using service
            String response = chatService.generateResponse(prompt);
            
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
}