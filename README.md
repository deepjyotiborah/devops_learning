# DemoService - FastAPI REST API

A production-ready REST API service built with FastAPI, featuring proper error handling, comprehensive unit tests, and complete documentation.

## Features

- ✅ RESTful API with health check endpoint
- ✅ Comprehensive error handling with custom exceptions
- ✅ Request/Response logging middleware
- ✅ CORS support
- ✅ Unit tests with pytest
- ✅ Interactive API documentation (Swagger/ReDoc)
- ✅ Configuration management with environment variables
- ✅ Type hints and Pydantic models
- ✅ Production-ready structure

## Project Structure

```
demoService/
├── app/
│   ├── __init__.py          # Package initialization
│   ├── main.py              # FastAPI application and error handlers
│   ├── config.py            # Configuration and settings
│   ├── routes.py            # API route definitions
│   ├── models.py            # Pydantic models
│   ├── exceptions.py        # Custom exceptions
│   └── middleware.py        # Custom middleware
├── tests/
│   ├── __init__.py
│   ├── conftest.py          # Pytest fixtures
│   ├── test_health.py       # Health endpoint tests
│   ├── test_root.py         # Root endpoint tests
│   ├── test_error_handling.py  # Error handling tests
│   └── test_middleware.py   # Middleware tests
├── requirements.txt         # Python dependencies
├── pytest.ini              # Pytest configuration
├── .gitignore              # Git ignore rules
└── README.md               # This file
```

## Requirements

- Python 3.10+
- pip or conda

## Installation

### 1. Clone the repository

```bash
cd /path/to/demoService
```

### 2. Create a virtual environment

```bash
# Using venv
python -m venv venv

# Activate on macOS/Linux
source venv/bin/activate

# Activate on Windows
venv\Scripts\activate
```

### 3. Install dependencies

```bash
python3 -m pip install -r requirements.txt
```

### 4. Configure environment (optional)

Create a `.env` file in the project root:

```env
APP_NAME=DemoService
APP_VERSION=1.0.0
APP_DESCRIPTION=A production-ready FastAPI demo service
ENVIRONMENT=development
DEBUG=True
LOG_LEVEL=INFO
```

## Running the Application

### Development Mode

```bash
# From project root
python -m app.main
```

Or using uvicorn directly:

```bash
uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

The API will be available at `http://localhost:8000`

### Production Mode

```bash
uvicorn app.main:app --host 0.0.0.0 --port 8000 --workers 4
```

## API Documentation

Once the application is running, visit:

- **Swagger UI**: http://localhost:8000/docs
- **ReDoc**: http://localhost:8000/redoc
- **OpenAPI JSON**: http://localhost:8000/openapi.json

## API Endpoints

### Root Endpoint

**GET /**

Returns basic API information.

**Response:**
```json
{
  "name": "DemoService",
  "version": "1.0.0",
  "description": "A production-ready FastAPI demo service",
  "docs_url": "/docs",
  "health_url": "/health"
}
```

### Health Check Endpoint

**GET /health**

Returns the health status of the service.

**Response (200 OK):**
```json
{
  "status": "healthy",
  "timestamp": "2025-10-11T12:00:00.000000",
  "version": "1.0.0",
  "environment": "development"
}
```

**Response Fields:**
- `status` (string): Service health status ("healthy")
- `timestamp` (datetime): Current server timestamp in ISO format
- `version` (string): Application version
- `environment` (string): Current environment (development/staging/production)

**Error Response (503 Service Unavailable):**
```json
{
  "error": "ServiceUnavailableError",
  "message": "Service unavailable",
  "detail": "Additional error details"
}
```

## Testing

### Run all tests

```bash
pytest
```

### Run tests with coverage

```bash
pytest --cov=app --cov-report=html
```

### Run specific test file

```bash
pytest tests/test_health.py
```

### Run tests with verbose output

```bash
pytest -v
```

## Test Coverage

The test suite includes:

- **Health Check Tests** (`test_health.py`)
  - Successful health check
  - Response structure validation
  - Timestamp format validation
  - Version verification
  - Custom headers validation

- **Root Endpoint Tests** (`test_root.py`)
  - Successful response
  - Response structure validation
  - Content verification

- **Error Handling Tests** (`test_error_handling.py`)
  - 404 Not Found errors
  - Error response structure
  - Method not allowed errors
  - Invalid endpoint formats

- **Middleware Tests** (`test_middleware.py`)
  - Custom header injection
  - Process time tracking
  - CORS headers

## Error Handling

The API provides comprehensive error handling with consistent error response format:

```json
{
  "error": "ErrorType",
  "message": "Human-readable error message",
  "detail": "Additional error details (optional)"
}
```

### HTTP Status Codes

- `200` - OK: Request successful
- `404` - Not Found: Endpoint doesn't exist
- `405` - Method Not Allowed: HTTP method not supported
- `422` - Unprocessable Entity: Validation error
- `500` - Internal Server Error: Server error
- `503` - Service Unavailable: Service is down

### Custom Exceptions

- `DemoServiceException`: Base exception for all custom errors
- `ServiceUnavailableError`: Service unavailable (503)
- `ResourceNotFoundError`: Resource not found (404)
- `ValidationError`: Validation failed (422)

## Middleware

### Logging Middleware

Automatically logs all requests and responses with:
- HTTP method and path
- Client IP address
- Response status code
- Request processing time

Adds custom header: `X-Process-Time` with request duration

### CORS Middleware

Configured to allow cross-origin requests. Configure `allow_origins` in `main.py` for production use.

## Configuration

Configuration is managed through environment variables and the `Settings` class in `app/config.py`.

### Available Settings

| Variable | Default | Description |
|----------|---------|-------------|
| `APP_NAME` | DemoService | Application name |
| `APP_VERSION` | 1.0.0 | Application version |
| `APP_DESCRIPTION` | ... | Application description |
| `ENVIRONMENT` | development | Environment (development/staging/production) |
| `DEBUG` | True | Debug mode |
| `LOG_LEVEL` | INFO | Logging level (DEBUG/INFO/WARNING/ERROR) |

## Development

### Code Style

- Follow PEP 8 guidelines
- Use type hints
- Add docstrings to all functions and classes
- Keep functions small and focused

### Adding New Endpoints

1. Define Pydantic models in `app/models.py`
2. Add route handler in `app/routes.py`
3. Add custom exceptions in `app/exceptions.py` if needed
4. Create unit tests in `tests/`

### Example: Adding a New Endpoint

```python
# app/routes.py
@router.get("/example")
async def example_endpoint():
    """Example endpoint"""
    return {"message": "Hello, World!"}

# tests/test_example.py
def test_example_endpoint(client):
    """Test example endpoint"""
    response = client.get("/example")
    assert response.status_code == 200
    assert response.json()["message"] == "Hello, World!"
```

## Docker Support (Optional)

Create a `Dockerfile`:

```dockerfile
FROM python:3.11-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 8000

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
```

Build and run:

```bash
docker build -t demoservice .
docker run -p 8000:8000 demoservice
```

## Logging

The application uses Python's built-in logging module. Logs include:

- Application startup/shutdown
- Request/response information
- Errors and exceptions
- Processing time for each request

Log level can be configured via `LOG_LEVEL` environment variable.

## Best Practices

- ✅ Type hints for better IDE support and type checking
- ✅ Pydantic models for request/response validation
- ✅ Custom exceptions for clear error handling
- ✅ Middleware for cross-cutting concerns
- ✅ Environment-based configuration
- ✅ Comprehensive unit tests
- ✅ API documentation (auto-generated)
- ✅ Proper project structure
- ✅ Logging and monitoring

## Troubleshooting

### Port already in use

```bash
# Find process using port 8000
lsof -i :8000

# Kill the process
kill -9 <PID>
```

### Import errors

Make sure you're in the project root directory and virtual environment is activated.

### Tests failing

```bash
# Clean pytest cache
pytest --cache-clear

# Reinstall dependencies
pip install -r requirements.txt --force-reinstall
```

## License

This project is provided as-is for educational and demonstration purposes.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## Support

For issues, questions, or contributions, please open an issue in the repository.

---

Built with ❤️ using [FastAPI](https://fastapi.tiangolo.com/)





