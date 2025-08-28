package SelfSens.SelfSens.exception;

import SelfSens.SelfSens.constants.ApplicationConstants;
import SelfSens.SelfSens.dto.ApiResponse;
import SelfSens.SelfSens.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        String path = RequestUtils.getCurrentRequestPath();
        
        logger.error("{} Generic exception occurred: {}", ApplicationConstants.LOG_PREFIX, ex.getMessage(), ex);
        
        ApiResponse<String> response = ApiResponse.error(
            ApplicationConstants.GENERIC_ERROR_MESSAGE + ": " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        response.setPath(path);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(ValidationException ex) {
        String path = RequestUtils.getCurrentRequestPath();
        
        logger.warn("{} Validation exception: {}", ApplicationConstants.LOG_PREFIX, ex.getMessage());
        
        Map<String, Object> errors = new HashMap<>();
        if (ex.hasFieldErrors()) {
            errors.putAll(ex.getFieldErrors());
        }
        
        ApiResponse<String> response = ApiResponse.error(
            ApplicationConstants.VALIDATION_ERROR_MESSAGE + ": " + ex.getMessage(),
            errors,
            HttpStatus.BAD_REQUEST
        );
        response.setPath(path);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ApiResponse<String>> handleChatException(ChatException ex) {
        String path = RequestUtils.getCurrentRequestPath();
        
        logger.error("{} Chat exception: {}", ApplicationConstants.LOG_PREFIX, ex.getMessage(), ex);
        
        ApiResponse<String> response = ApiResponse.error(
            ApplicationConstants.CHAT_ERROR_MESSAGE + ": " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        response.setPath(path);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        String path = RequestUtils.getCurrentRequestPath();
        
        logger.warn("{} Illegal argument exception: {}", ApplicationConstants.LOG_PREFIX, ex.getMessage());
        
        ApiResponse<String> response = ApiResponse.error(
            "Invalid argument provided: " + ex.getMessage(),
            HttpStatus.BAD_REQUEST
        );
        response.setPath(path);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String path = RequestUtils.getCurrentRequestPath();
        
        logger.warn("{} Method argument type mismatch: {}", ApplicationConstants.LOG_PREFIX, ex.getMessage());
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("parameter", ex.getName());
        errors.put("value", ex.getValue());
        errors.put("expectedType", ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        
        ApiResponse<String> response = ApiResponse.error(
            "Parameter type mismatch: " + ex.getName(),
            errors,
            HttpStatus.BAD_REQUEST
        );
        response.setPath(path);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoHandlerFound(NoHandlerFoundException ex) {
        logger.warn("{} No handler found: {} {}", ApplicationConstants.LOG_PREFIX, ex.getHttpMethod(), ex.getRequestURL());
        
        Map<String, Object> errors = new HashMap<>();
        errors.put("method", ex.getHttpMethod());
        errors.put("path", ex.getRequestURL());
        
        ApiResponse<String> response = ApiResponse.error(
            ApplicationConstants.NOT_FOUND_MESSAGE,
            errors,
            HttpStatus.NOT_FOUND
        );
        response.setPath(ex.getRequestURL());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        String path = RequestUtils.getCurrentRequestPath();
        
        logger.error("{} Runtime exception: {}", ApplicationConstants.LOG_PREFIX, ex.getMessage(), ex);
        
        ApiResponse<String> response = ApiResponse.error(
            "Runtime error: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        response.setPath(path);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
