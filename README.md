# SelfSens Chat API

A Spring Boot application with integrated AI chat functionality using Google Gemini 2.0 Flash, built with professional package structure and loose coupling architecture.

## 🏗️ Project Structure

The project follows a professional layered architecture pattern with loose coupling:

```
src/main/java/SelfSens/SelfSens/
├── config/                 # Configuration classes
│   ├── VertexAiConfig.java
│   └── EnvironmentConfig.java
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
│   ├── HealthService.java
│   ├── AiModelService.java
│   ├── GeminiAiService.java
│   ├── MockAiService.java
│   └── AiServiceFactory.java
├── util/                   # Utility classes
│   └── RequestUtils.java
├── validation/             # Input validation
│   └── ChatRequestValidator.java
└── SelfSensApplication.java
```

## ✨ Features

- **AI-powered chat** using Google Gemini 2.0 Flash
- **Loose coupling architecture** for easy AI provider switching
- **Professional package organization** following Spring Boot best practices
- **Structured JSON API responses** with consistent error handling
- **Comprehensive validation** with custom validators
- **Global exception handling** with detailed error responses
- **Proper logging** with SLF4J and Logback
- **Health check endpoint** for monitoring
- **Input sanitization** and content filtering
- **Environment-based configuration** for API keys
- **Fallback to mock service** when AI is unavailable

## 🚀 Current Status

✅ **COMPLETED:**
- Professional package structure
- Structured API response system
- Global exception handling
- Input validation and sanitization
- Health check endpoint
- **Google Gemini 2.0 Flash integration**
- **Loose coupling architecture**
- **Environment variable configuration**
- **AI service factory pattern**
- **Fallback mock service**
- Comprehensive logging
- Professional error handling

🔄 **NEXT STEPS:**
- Add more AI providers (OpenAI, Claude, etc.)
- Implement authentication and security
- Add rate limiting
- Add monitoring and metrics
- Docker containerization

## 🤖 AI Integration Architecture

### Loose Coupling Design
The application uses a factory pattern and interface-based design to easily switch between different AI providers:

```
ChatService → AiServiceFactory → AiModelService (Interface)
                                    ↓
                    ┌─────────────────────────────┐
                    │                             │
            GeminiAiService              MockAiService
            (Real AI)                   (Fallback)
```

### Available AI Services
1. **Google Gemini 2.0 Flash** - Primary AI service
2. **Mock AI Service** - Fallback service with intelligent responses

### Easy Provider Switching
- Set `AI_PROVIDER` environment variable
- Automatically falls back to available services
- No code changes required to switch providers

## 📡 API Response Format

All API endpoints return responses in a consistent JSON format using the `ApiResponse<T>` class:

### Success Response Example
```json
{
  "success": true,
  "message": "Chat response generated successfully",
  "data": "Java is a high-level, class-based, object-oriented programming language...",
  "statusCode": 200,
  "timestamp": "2025-08-29T02:09:01.117156700",
  "path": "/api/chat/prompt"
}
```

### Error Response Example
```json
{
  "success": false,
  "message": "Validation failed: Chat prompt validation failed",
  "statusCode": 400,
  "timestamp": "2025-08-29T02:09:01.117156700",
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
  - `prompt` (optional): The message to send to AI (default: "Tell me a joke about spring boot.")
- **Response**: AI-generated response wrapped in ApiResponse format
- **Validation**: Input length, content filtering, and sanitization

### 2. Health Check
- **URL**: `GET /api/health`
- **Response**: Service health status, version, and system information

### 3. AI Service Information
- **URL**: `GET /api/chat/service-info`
- **Response**: Current AI service provider and configuration

### 4. Available Services
- **URL**: `GET /api/chat/available-services`
- **Response**: List of all available AI services and their status

## ⚙️ Configuration

### Environment Variables
Set these environment variables for AI configuration:

```bash
# Required: Google API Key
export GOOGLE_API_KEY="your_google_api_key_here"

# Optional: AI Configuration
export GEMINI_MODEL="gemini-2.0-flash"
export GEMINI_TEMPERATURE="0.7"
export GEMINI_MAX_TOKENS="1000"
export AI_PROVIDER="gemini"
```

### Application Properties
```properties
# AI Configuration
spring.ai.google.generative.ai.api-key=${GOOGLE_API_KEY:}
spring.ai.google.generative.ai.model=${GEMINI_MODEL:gemini-2.0-flash}
spring.ai.google.generative.ai.temperature=${GEMINI_TEMPERATURE:0.7}
spring.ai.google.generative.ai.max-output-tokens=${GEMINI_MAX_TOKENS:1000}
```

### Environment File
Copy `env.example` to `.env` and configure your API keys:

```bash
cp env.example .env
# Edit .env with your actual API keys
```

## 🚀 Running the Application

### Prerequisites
- Java 21+ installed
- Maven 3.6+ installed
- Google API key for Gemini (optional, will fallback to mock)

### Quick Start
1. **Clone and navigate to project**
   ```bash
   cd SelfSens
   ```

2. **Configure environment variables**
   ```bash
   export GOOGLE_API_KEY="your_api_key_here"
   ```

3. **Build the project**
   ```bash
   ./mvnw clean compile
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access the API**
   - Base URL: `http://localhost:8080/api/`
   - Chat: `http://localhost:8080/api/chat/prompt`
   - Health: `http://localhost:8080/api/health`
   - Service Info: `http://localhost:8080/api/chat/service-info`

## 🧪 Testing the API

### Health Check
```bash
curl http://localhost:8080/api/health
```

### Chat API (with Gemini)
```bash
curl "http://localhost:8080/api/chat/prompt?prompt=What is Java programming?"
```

### Service Information
```bash
curl http://localhost:8080/api/chat/service-info
```

### Available Services
```bash
curl http://localhost:8080/api/chat/available-services
```

### Error Handling Test
```bash
curl "http://localhost:8080/api/chat/prompt?prompt="
```

## 📚 Dependencies

### Core Dependencies
- **Spring Boot 3.5.5** - Core framework
- **Spring Web** - REST API support
- **Spring AI 1.0.1** - AI integration framework
- **Google Generative AI** - Gemini integration
- **Jackson** - JSON serialization
- **SLF4J + Logback** - Logging framework
- **Spring Validation** - Input validation

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

## 🏗️ Architecture Patterns

### 1. Factory Pattern
- `AiServiceFactory` dynamically selects AI services
- Easy to add new AI providers
- Automatic fallback to available services

### 2. Strategy Pattern
- `AiModelService` interface allows different AI implementations
- Easy to switch between AI providers
- Mock service for development/testing

### 3. Dependency Injection
- Spring-managed beans for loose coupling
- Easy to test and mock services
- Configuration-driven service selection

### 4. Template Method Pattern
- Consistent API response format
- Standardized error handling
- Reusable validation logic

## 📈 Best Practices Implemented

1. **Separation of Concerns** - Clear package structure
2. **Loose Coupling** - Interface-based design
3. **Factory Pattern** - Dynamic service selection
4. **Environment Configuration** - Secure API key management
5. **Exception Handling** - Centralized error management
6. **Input Validation** - Comprehensive request validation
7. **Logging** - Structured logging with appropriate levels
8. **Constants Management** - Centralized configuration
9. **Service Layer** - Business logic separation
10. **DTO Pattern** - Consistent response format
11. **Utility Classes** - Reusable helper methods
12. **Professional Naming** - Clear and descriptive class names

## 🔧 Troubleshooting

### Common Issues

1. **AI Service Not Available**
   - Check if `GOOGLE_API_KEY` is set
   - Verify API key is valid
   - Check logs for specific error messages

2. **Port Already in Use**
   - Change `server.port` in `application.properties`
   - Or set `SERVER_PORT` environment variable

3. **Build Failures**
   - Run `./mvnw clean compile` to rebuild
   - Check Java version (requires Java 21+)

4. **AI Response Errors**
   - Check Google API quota and limits
   - Verify model name is correct
   - Check network connectivity

### Logs
- Application logs are displayed in the console
- Log level can be configured in `application.properties`
- Custom logback configuration in `logback-spring.xml`

## 🚀 Next Steps

1. **Add More AI Providers**
   - OpenAI GPT integration
   - Anthropic Claude integration
   - Local AI models (Ollama, etc.)

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
   - API key rotation

4. **Performance Optimization**
   - Response caching
   - Async processing
   - Connection pooling
   - Load balancing

## 📞 Support

This application is built with enterprise-grade architecture and follows Spring Boot best practices. The loose coupling design makes it easy to add new AI providers and switch between them without code changes.

---

**Note**: The application automatically falls back to the mock service if the Gemini API is not configured or unavailable. Set the `GOOGLE_API_KEY` environment variable to enable real AI functionality.
