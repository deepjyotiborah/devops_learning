# DemoService - Spring Boot REST API

A production-ready Spring Boot application demonstrating RESTful API best practices with health monitoring and user management.

## Features

- ✅ **Custom Health Endpoint** - `/health` endpoint with system monitoring and appropriate HTTP status codes
- ✅ **Swagger Documentation** - Interactive API documentation with Swagger UI
- ✅ **Spring Boot Actuator** - Additional actuator endpoints for production monitoring
- ✅ **RESTful API** - Complete CRUD operations for User management
- ✅ **Input Validation** - Request validation using Bean Validation
- ✅ **Exception Handling** - Global exception handler for consistent error responses
- ✅ **System Monitoring** - Comprehensive system information (memory, CPU, JVM, disk, OS details)
- ✅ **Best Practices** - Clean architecture with Controller-Service-Model layers
- ✅ **Unit Tests** - Comprehensive test coverage

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Gradle 8.5**
- **Spring Web** - RESTful API
- **Spring Boot Actuator** - Health monitoring
- **Springfox Swagger** - API documentation
- **Bean Validation** - Input validation
- **JUnit 5** - Testing framework

## Project Structure

```
demoService/
├── src/
│   ├── main/
│   │   ├── java/com/example/demoservice/
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── HealthController.java
│   │   │   │   └── UserController.java
│   │   │   ├── service/             # Business Logic
│   │   │   │   └── UserService.java
│   │   │   ├── model/               # Data Models
│   │   │   │   └── User.java
│   │   │   ├── health/              # Custom Health Indicators
│   │   │   │   └── SystemHealthIndicator.java
│   │   │   ├── config/              # Configuration Classes
│   │   │   │   └── SwaggerConfig.java
│   │   │   ├── exception/           # Exception Handling
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── DemoServiceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                        # Unit Tests
├── build.gradle
├── settings.gradle
└── README.md
```

## Prerequisites

- Java 17 or higher
- Gradle 8.5+ (or use included Gradle wrapper)

## Getting Started

### 1. Clone/Navigate to the project directory

```bash
cd demoService
```

### 2. Build the project

```bash
./gradlew build
```

### 3. Run the application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## API Documentation

### Swagger UI (Interactive Documentation)

Once the application is running, you can access the interactive API documentation at:

**🔗 http://localhost:8080/swagger-ui/**

The Swagger UI provides:
- Complete API documentation
- Interactive API testing
- Request/response examples
- Schema definitions

### Swagger API Docs

The Swagger v2 specification (JSON format) is available at:

**🔗 http://localhost:8080/v2/api-docs**

## API Endpoints

### Health Check Endpoint

**GET /health**
- Custom health endpoint that returns application health status with system information
- Uses appropriate HTTP status codes (200 OK when healthy, 503 Service Unavailable when unhealthy)
- Includes: memory usage, JVM details, OS info, CPU, and disk metrics
- Health determination based on memory and disk usage thresholds
- Response (200 OK when healthy):
```json
{
  "service": "DemoService",
  "timestamp": "2025-10-05T10:30:00",
  "status": "UP",
  "message": "Service is running successfully",
  "system": {
    "memory": {
      "max": "4.00 GB",
      "total": "512.00 MB",
      "free": "256.00 MB",
      "used": "256.00 MB",
      "usagePercent": "50.00%",
      "status": "healthy"
    },
    "jvm": {
      "name": "OpenJDK 64-Bit Server VM",
      "vendor": "Oracle Corporation",
      "version": "17.0.8",
      "uptime": "5m 30s"
    },
    "os": {
      "name": "Mac OS X",
      "version": "14.5",
      "architecture": "aarch64",
      "systemLoadAverage": "2.34"
    },
    "cpu": {
      "availableProcessors": 8
    },
    "disk": {
      "total": "500.00 GB",
      "free": "200.00 GB",
      "usable": "200.00 GB",
      "used": "300.00 GB",
      "usagePercent": "60.00%",
      "status": "healthy"
    }
  }
}
```

### Spring Boot Actuator Endpoints

**GET /actuator/health**
- Spring Boot Actuator health endpoint with custom system health indicator
- Includes built-in health checks and custom system metrics

**GET /actuator/info**
- Returns application information
- Response:
```json
{
  "app": {
    "name": "DemoService",
    "description": "Spring Boot Demo Service with REST API",
    "version": "1.0.0"
  }
}
```

### User Management API

#### Get All Users
**GET /api/users**
- Returns list of all users

#### Get User by ID
**GET /api/users/{id}**
- Returns a specific user by ID

#### Create User
**POST /api/users**
- Creates a new user
- Request Body:
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

#### Update User
**PUT /api/users/{id}**
- Updates an existing user
- Request Body:
```json
{
  "name": "John Updated",
  "email": "john.updated@example.com"
}
```

#### Delete User
**DELETE /api/users/{id}**
- Deletes a user by ID

## Testing the API

### Using Swagger UI

1. Start the application: `./gradlew bootRun`
2. Open your browser and navigate to: `http://localhost:8080/swagger-ui/`
3. Try out the API endpoints interactively!

### Using curl

```bash
# Health Check (Custom endpoint)
curl http://localhost:8080/health

# Spring Boot Actuator Health
curl http://localhost:8080/actuator/health

# Application Info
curl http://localhost:8080/actuator/info

# Swagger API Docs
curl http://localhost:8080/v2/api-docs

# Get all users
curl http://localhost:8080/api/users

# Get user by ID
curl http://localhost:8080/api/users/1

# Create a new user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Johnson","email":"alice@example.com"}'

# Update a user
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"John Updated","email":"john.updated@example.com"}'

# Delete a user
curl -X DELETE http://localhost:8080/api/users/1
```

## Running Tests

```bash
./gradlew test
```

## Additional Endpoints

### API Documentation
- **Swagger UI**: `http://localhost:8080/swagger-ui/` - Interactive API documentation
- **Swagger API Docs**: `http://localhost:8080/v2/api-docs` - Swagger v2 specification (JSON)

### Spring Boot Actuator
- **Health**: `http://localhost:8080/actuator/health` - Comprehensive health status with system metrics
- **Info**: `http://localhost:8080/actuator/info` - Application information
- **Metrics**: `http://localhost:8080/actuator/metrics` - Application metrics

## Configuration

Application configuration can be modified in `src/main/resources/application.properties`:

- `server.port` - Server port (default: 8080)
- `spring.application.name` - Application name
- `management.endpoints.web.exposure.include` - Actuator endpoints to expose
- `logging.level.*` - Logging configuration

## Best Practices Implemented

1. **Layered Architecture** - Separation of concerns with Controller, Service, and Model layers
2. **RESTful Design** - Proper HTTP methods and status codes
3. **Input Validation** - Bean Validation annotations for data validation
4. **Exception Handling** - Global exception handler for consistent error responses
5. **Dependency Injection** - Constructor-based injection for better testability
6. **Clean Code** - Clear naming conventions and code organization
7. **Testing** - Unit tests with MockMvc for controller testing

## Building for Production

```bash
# Create executable JAR
./gradlew clean build

# Run the JAR
java -jar build/libs/demoService-1.0.0.jar
```

## License

This project is created for demonstration purposes.

## Author

DevOps Training Project

