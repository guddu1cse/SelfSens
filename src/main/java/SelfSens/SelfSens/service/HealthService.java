package SelfSens.SelfSens.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HealthService {
    
    public Map<String, Object> getHealthStatus() {
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("status", "UP");
        healthData.put("service", "SelfSens Chat API");
        healthData.put("version", "1.0.0");
        healthData.put("timestamp", System.currentTimeMillis());
        healthData.put("javaVersion", System.getProperty("java.version"));
        healthData.put("os", System.getProperty("os.name"));
        
        return healthData;
    }
}
