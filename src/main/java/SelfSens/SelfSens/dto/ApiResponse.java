package SelfSens.SelfSens.dto;

import SelfSens.SelfSens.entities.ProfileDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Map<String, Object> errors;
    private int statusCode;
    private String timestamp;
    private String path;

    // Constructors
    public ApiResponse() {
        this.timestamp = LocalDateTime.now().toString();
    }

    public ApiResponse(boolean success, String message, int statusCode) {
        this();
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
    }

    public ApiResponse(boolean success, String message, T data, int statusCode) {
        this(success, message, statusCode);
        this.data = data;
    }

    // Static factory methods for success responses
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operation completed successfully", data, HttpStatus.OK.value());
    }

    // Static factory methods for error responses
    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(false, message, status.value());
    }

    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(false, message, statusCode);
    }

    public static <T> ApiResponse<T> error(String message, Map<String, Object> errors, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(false, message, status.value());
        response.setErrors(errors);
        return response;
    }

    public static <T> ApiResponse<T> error(String message, String field, String error, HttpStatus status) {
        Map<String, Object> errors = new HashMap<>();
        errors.put(field, error);
        return error(message, errors, status);
    }

    // Method to add a single error
    public void addError(String field, Object error) {
        if (this.errors == null) {
            this.errors = new HashMap<>();
        }
        this.errors.put(field, error);
    }

    // Method to add multiple errors
    public void addErrors(Map<String, Object> additionalErrors) {
        if (this.errors == null) {
            this.errors = new HashMap<>();
        }
        this.errors.putAll(additionalErrors);
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
