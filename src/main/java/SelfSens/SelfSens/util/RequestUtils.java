package SelfSens.SelfSens.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {
    
    /**
     * Get the current HTTP request from the request context
     * @return HttpServletRequest or null if not available
     */
    public static HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Get the current request URI
     * @return request URI or "unknown" if not available
     */
    public static String getCurrentRequestPath() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? request.getRequestURI() : "unknown";
    }
    
    /**
     * Get the client's IP address
     * @return client IP address or "unknown" if not available
     */
    public static String getClientIpAddress() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return "unknown";
        }
        
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
