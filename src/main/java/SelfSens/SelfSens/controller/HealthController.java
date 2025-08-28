package SelfSens.SelfSens.controller;

import SelfSens.SelfSens.constants.ApplicationConstants;
import SelfSens.SelfSens.dto.ApiResponse;
import SelfSens.SelfSens.service.HealthService;
import SelfSens.SelfSens.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(ApplicationConstants.API_BASE_PATH + ApplicationConstants.HEALTH_ENDPOINT)
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);
    
    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        try {
            String requestPath = RequestUtils.getCurrentRequestPath();
            
            logger.info("{} Health check requested", ApplicationConstants.LOG_PREFIX);
            
            Map<String, Object> healthData = healthService.getHealthStatus();
            
            ApiResponse<Map<String, Object>> response = ApiResponse.success(
                ApplicationConstants.HEALTH_CHECK_SUCCESS, 
                healthData
            );
            response.setPath(requestPath);
            
            logger.info("{} Health check completed successfully", ApplicationConstants.LOG_PREFIX);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("{} Error during health check: {}", ApplicationConstants.LOG_PREFIX, e.getMessage(), e);
            
            ApiResponse<Map<String, Object>> response = ApiResponse.error(
                "Health check failed: " + e.getMessage(),
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
            );
            response.setPath(RequestUtils.getCurrentRequestPath());
            
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
