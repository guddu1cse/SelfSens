# SelfSens Chat API

A Spring Boot application with a professional package structure, built with best practices for enterprise development.

## 🏗️ Project Structure

The project follows a professional layered architecture pattern:

```
src/main/java/SelfSens/SelfSens/
├── config/                 # Configuration classes
│   └── VertexAiConfig.java
├── constants/              # Application constants
│   └── ApplicationConstants.java
├── controller/             # REST API controllers
│   ├── ChatController.java
│   └── HealthController.java
├── dto/                    # Data Transfer Objects
│   └── ApiResponse.java
├── exception/              # Custom exceptions and handlers
│   ├── ChatException.java
│   ├── ValidationException.java
│   └── GlobalExceptionHandler.java
├── service/                # Business logic services
│   ├── ChatService.java
│   └── HealthService.java
├── util/                   # Utility classes
│   └── RequestUtils.java
├── validation/             # Input validation
│   └── ChatRequestValidator.java
└── SelfSensApplication.java
```

## ✨ Features

- **Professional package organization** following Spring Boot best practices
- **Structured JSON API responses** with consistent error handling
- **Comprehensive validation** with custom validators
- **Global exception handling** with detailed error responses
- **Proper logging** with SLF4J and Logback
- **Health check endpoint** for monitoring
- **Input sanitization** and content filtering
- **Mock chat service** (ready for AI integration)

## 🚀 Current Status

✅ **COMPLETED:**
- Professional package structure
- Structured API response system
- Global exception handling
- Input validation and sanitization
- Health check endpoint
- Mock chat service
- Comprehensive logging
- Professional error handling

⚠️ **TEMPORARILY DISABLED:**
- Spring AI integration (due to dependency resolution issues)
- Google Vertex AI integration

🔄 **NEXT STEPS:**
- Resolve Spring AI dependency issues
- Integrate with actual AI service
- Add authentication and security
- Implement rate limiting
- Add monitoring and metrics

## 📡 API Response Format

All API endpoints return responses in a consistent JSON format using the `ApiResponse<T>` class:

### Success Response Example
```json
{
  "success": true,
  "message": "Chat response generated successfully",
  "data": "Here's a joke about Spring Boot: Why did the Spring Boot developer go broke? Because he used too many @Autowired annotations!",
  "statusCode": 200,
  "timestamp": "2024-01-15T10:30:45.123",
  "path": "/api/chat/prompt"
}
```

### Error Response Example
```json
{
  "success": false,
  "message": "Validation failed: Chat prompt validation failed",
  "statusCode": 400,
  "timestamp": "2024-01-15T10:30:45.123",
  "path": "/api/chat/prompt",
  "errors": {
    "prompt": "Prompt cannot be empty"
  }
}
```

## 🚀 API Endpoints

### 1. Chat Endpoint
- **URL**: `GET /api/chat/prompt`
- **Query Parameters**: 
  - `prompt` (optional): The message to send (default: "Tell me a joke about spring boot.")
- **Response**: Mock chat response wrapped in ApiResponse format
- **Validation**: Input length, content filtering, and sanitization

### 2. Health Check
- **URL**: `GET /api/health`
- **Response**: Service health status, version, and system information

## 🛡️ Validation & Security

### Input Validation
- **Prompt Length**: 1-1000 characters
- **Content Filtering**: Blocks potentially harmful content
- **Sanitization**: Input cleaning and validation

### Error Handling
- **Global Exception Handler**: Catches all exceptions and formats them consistently
- **Custom Exceptions**: Specific exception types for different error scenarios
- **Detailed Error Messages**: Field-specific error information

## ⚙️ Configuration

### Current Configuration (`application.properties`)
```properties
# Application Configuration
spring.application.name=SelfSens
server.port=8080

# Logging Configuration
logging.level.SelfSens.SelfSens=INFO
logging.level.org.springframework=INFO
```

### Future AI Configuration (when dependencies are resolved)
```properties
# Vertex AI Configuration
spring.ai.vertex.ai.chat.api-key=YOUR_API_KEY
spring.ai.vertex.ai.chat.project-id=YOUR_PROJECT_ID
spring.ai.vertex.ai.chat.location=asia/kolkata
spring.ai.vertex.ai.chat.model=gemini-1.5-flash
```

## 🚀 Running the Application

### Prerequisites
- Java 21+ installed
- Maven 3.6+ installed

### Quick Start
1. **Clone and navigate to project**
   ```bash
   cd SelfSens
   ```

2. **Build the project**
   ```bash
   ./mvnw clean compile
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the API**
   - Base URL: `http://localhost:8080/api/`
   - Chat: `http://localhost:8080/api/chat/prompt`
   - Health: `http://localhost:8080/api/health`

## 🧪 Testing the API

### Health Check
```bash
curl http://localhost:8080/api/health
```

### Chat API (Mock)
```bash
curl "http://localhost:8080/api/chat/prompt?prompt=Tell me a joke"
```

### Error Handling Test
```bash
curl "http://localhost:8080/api/chat/prompt?prompt="
```

## 📚 Dependencies

### Current Dependencies
- **Spring Boot 3.5.5** - Core framework
- **Spring Web** - REST API support
- **Jackson** - JSON serialization
- **SLF4J + Logback** - Logging framework
- **Spring Validation** - Input validation

### Future Dependencies (when resolved)
- **Spring AI 1.0.1** - AI integration
- **Google Vertex AI** - AI model integration

## 🔍 Error Handling

The application includes comprehensive error handling:

### Exception Types
- **ChatException** - Chat-related errors
- **ValidationException** - Input validation failures
- **IllegalArgumentException** - Invalid arguments
- **MethodArgumentTypeMismatchException** - Parameter type mismatches
- **NoHandlerFoundException** - Endpoint not found
- **RuntimeException** - Runtime errors
- **Generic Exception** - All other exceptions

### Error Response Features
- **Consistent Format** - All errors follow the same structure
- **HTTP Status Codes** - Proper HTTP status codes for different error types
- **Detailed Messages** - Clear error descriptions
- **Field-level Errors** - Specific error information for validation failures
- **Request Path** - Endpoint information for debugging
- **Timestamps** - Error occurrence timing

## 📈 Best Practices Implemented

1. **Separation of Concerns** - Clear package structure
2. **Dependency Injection** - Proper Spring DI usage
3. **Exception Handling** - Centralized error management
4. **Input Validation** - Comprehensive request validation
5. **Logging** - Structured logging with appropriate levels
6. **Constants Management** - Centralized configuration
7. **Service Layer** - Business logic separation
8. **DTO Pattern** - Consistent response format
9. **Utility Classes** - Reusable helper methods
10. **Professional Naming** - Clear and descriptive class names

## 🔧 Troubleshooting

### Common Issues

1. **Port Already in Use**
   - Change `server.port` in `application.properties`
   - Or kill the process using port 8080

2. **Build Failures**
   - Run `./mvnw clean compile` to rebuild
   - Check Java version (requires Java 21+)

3. **Application Not Starting**
   - Check logs for error messages
   - Verify all dependencies are resolved

### Logs
- Application logs are displayed in the console
- Log level can be configured in `application.properties`
- Custom logback configuration in `logback-spring.xml`

## 🚀 Next Steps

1. **Resolve Spring AI Dependencies**
   - Investigate Spring AI 1.0.1 compatibility
   - Update to latest stable version if needed
   - Test with actual AI service

2. **Add Production Features**
   - Authentication and authorization
   - Rate limiting
   - API documentation (OpenAPI/Swagger)
   - Monitoring and metrics
   - Docker containerization

3. **Enhance Security**
   - Input sanitization improvements
   - CORS configuration
   - Security headers
   - API key management

## 📞 Support

This application is built with enterprise-grade architecture and follows Spring Boot best practices. The current mock implementation provides a solid foundation for adding real AI functionality once dependency issues are resolved.

---

**Note**: The application is currently running with a mock chat service. To enable real AI functionality, resolve the Spring AI dependency issues and configure the appropriate AI service credentials.
