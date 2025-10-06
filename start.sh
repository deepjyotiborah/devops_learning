#!/bin/bash

echo "Starting DemoService..."
echo "======================================"
echo ""
echo "The application will be available at:"
echo "  - Swagger UI: http://localhost:8080/swagger-ui.html"
echo "  - Health Check: http://localhost:8080/health"
echo "  - Actuator Health: http://localhost:8080/actuator/health"
echo "  - API Endpoints: http://localhost:8080/api/users"
echo ""
echo "Press Ctrl+C to stop the application"
echo "======================================"
echo ""

./gradlew bootRun

