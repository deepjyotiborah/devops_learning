# API Documentation

Complete API documentation for DemoService.

## Base URL

```
http://localhost:8000
```

## Authentication

Currently, no authentication is required. For production use, implement OAuth2, JWT, or API key authentication.

## Endpoints

### 1. Root Endpoint

Get basic API information.

**Endpoint:** `GET /`

**Tags:** Root

**Response Code:** 200 OK

**Response Body:**

```json
{
  "name": "DemoService",
  "version": "1.0.0",
  "description": "A production-ready FastAPI demo service",
  "docs_url": "/docs",
  "health_url": "/health"
}
```

**Example Request:**

```bash
curl -X GET http://localhost:8000/
```

**Example Response:**

```json
{
  "name": "DemoService",
  "version": "1.0.0",
  "description": "A production-ready FastAPI demo service",
  "docs_url": "/docs",
  "health_url": "/health"
}
```

---

### 2. Health Check

Check service health and availability.

**Endpoint:** `GET /health`

**Tags:** Health

**Description:** Returns the health status of the service along with metadata including timestamp, version, and environment.

**Response Codes:**
- `200 OK` - Service is healthy
- `503 Service Unavailable` - Service is unavailable

**Success Response (200 OK):**

```json
{
  "status": "healthy",
  "timestamp": "2025-10-11T12:00:00.123456",
  "version": "1.0.0",
  "environment": "development"
}
```

**Response Fields:**

| Field | Type | Description |
|-------|------|-------------|
| `status` | string | Service health status (always "healthy" on success) |
| `timestamp` | datetime | Current server timestamp in ISO 8601 format |
| `version` | string | Application version |
| `environment` | string | Current deployment environment |

**Error Response (503 Service Unavailable):**

```json
{
  "error": "ServiceUnavailableError",
  "message": "Service unavailable",
  "detail": "Additional error details"
}
```

**Example Request:**

```bash
curl -X GET http://localhost:8000/health
```

**Example Response:**

```json
{
  "status": "healthy",
  "timestamp": "2025-10-11T12:34:56.789012",
  "version": "1.0.0",
  "environment": "development"
}
```

**Use Cases:**
- Kubernetes/Docker health checks
- Load balancer health probes
- Monitoring and alerting systems
- CI/CD pipeline validation

---

## Response Headers

All responses include the following custom headers:

| Header | Description |
|--------|-------------|
| `X-Process-Time` | Request processing time in seconds |
| `content-type` | Response content type (application/json) |
| `access-control-allow-origin` | CORS header (configured for cross-origin requests) |

## Error Responses

All error responses follow a consistent format:

```json
{
  "error": "ErrorType",
  "message": "Human-readable error message",
  "detail": "Additional error details (optional)"
}
```

### Common Error Types

#### 404 Not Found

**Trigger:** Requesting a non-existent endpoint

**Response:**

```json
{
  "error": "NotFoundError",
  "message": "Endpoint not found",
  "detail": "The requested endpoint /invalid does not exist"
}
```

**Example:**

```bash
curl -X GET http://localhost:8000/nonexistent
```

#### 405 Method Not Allowed

**Trigger:** Using an unsupported HTTP method

**Response:**

```json
{
  "detail": "Method Not Allowed"
}
```

**Example:**

```bash
curl -X POST http://localhost:8000/health
```

#### 422 Unprocessable Entity

**Trigger:** Invalid request data or validation failure

**Response:**

```json
{
  "error": "RequestValidationError",
  "message": "Invalid request data",
  "detail": [
    {
      "loc": ["body", "field_name"],
      "msg": "field required",
      "type": "value_error.missing"
    }
  ]
}
```

#### 500 Internal Server Error

**Trigger:** Unexpected server error

**Response:**

```json
{
  "error": "InternalServerError",
  "message": "An internal error occurred",
  "detail": "Error details (only in debug mode)"
}
```

#### 503 Service Unavailable

**Trigger:** Service is temporarily unavailable

**Response:**

```json
{
  "error": "ServiceUnavailableError",
  "message": "Service unavailable",
  "detail": "Additional details about the unavailability"
}
```

## Status Codes

| Code | Description | When Used |
|------|-------------|-----------|
| 200 | OK | Successful request |
| 404 | Not Found | Endpoint doesn't exist |
| 405 | Method Not Allowed | HTTP method not supported for endpoint |
| 422 | Unprocessable Entity | Request validation failed |
| 500 | Internal Server Error | Unexpected server error |
| 503 | Service Unavailable | Service is temporarily unavailable |

## Rate Limiting

Currently, no rate limiting is implemented. For production use, consider implementing rate limiting using middleware or API gateway.

## Request Examples

### Python (requests)

```python
import requests

# Health check
response = requests.get('http://localhost:8000/health')
print(response.json())

# Root endpoint
response = requests.get('http://localhost:8000/')
print(response.json())
```

### Python (httpx - async)

```python
import httpx
import asyncio

async def check_health():
    async with httpx.AsyncClient() as client:
        response = await client.get('http://localhost:8000/health')
        print(response.json())

asyncio.run(check_health())
```

### cURL

```bash
# Health check
curl -X GET http://localhost:8000/health

# Root endpoint
curl -X GET http://localhost:8000/

# With headers
curl -X GET http://localhost:8000/health -H "Accept: application/json"
```

### JavaScript (fetch)

```javascript
// Health check
fetch('http://localhost:8000/health')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error:', error));

// Async/await
async function checkHealth() {
  try {
    const response = await fetch('http://localhost:8000/health');
    const data = await response.json();
    console.log(data);
  } catch (error) {
    console.error('Error:', error);
  }
}
```

### PowerShell

```powershell
# Health check
Invoke-RestMethod -Uri http://localhost:8000/health -Method Get

# Root endpoint
Invoke-WebRequest -Uri http://localhost:8000/ -Method Get | Select-Object -Expand Content
```

## Interactive API Documentation

FastAPI automatically generates interactive API documentation:

### Swagger UI

Access at: http://localhost:8000/docs

Features:
- Interactive API explorer
- Try out endpoints directly from browser
- View request/response schemas
- See all available endpoints

### ReDoc

Access at: http://localhost:8000/redoc

Features:
- Clean, responsive documentation
- Three-panel layout
- Easy navigation
- Downloadable specification

### OpenAPI Schema

Access raw OpenAPI JSON at: http://localhost:8000/openapi.json

Can be used with:
- API client generation tools
- Testing tools
- Documentation generators

## Best Practices

### Using the Health Check Endpoint

1. **Container Orchestration:**
   ```yaml
   # Kubernetes example
   livenessProbe:
     httpGet:
       path: /health
       port: 8000
     initialDelaySeconds: 10
     periodSeconds: 5
   ```

2. **Load Balancer:**
   ```
   Configure health check path: /health
   Expected status code: 200
   Timeout: 5 seconds
   Interval: 10 seconds
   ```

3. **Monitoring:**
   ```python
   # Monitor service availability
   import time
   import requests
   
   while True:
       try:
           response = requests.get('http://localhost:8000/health')
           if response.status_code == 200:
               print("Service is healthy")
           else:
               print("Service returned non-200 status")
       except Exception as e:
           print(f"Service is down: {e}")
       time.sleep(60)
   ```

## Versioning

Current API version: `1.0.0`

Version information is available:
- In the root endpoint response
- In the health check response
- In the API documentation

For future versions, consider implementing URL-based versioning:
- `/v1/health`
- `/v2/health`

## Support

For additional help:
1. Check the interactive documentation at `/docs`
2. Review the OpenAPI schema at `/openapi.json`
3. Refer to the main README.md for setup and configuration
4. Check the logs for detailed error information

---

Last Updated: October 11, 2025  
API Version: 1.0.0





