"""Unit tests for health check endpoint"""
import pytest
from datetime import datetime
from fastapi import status


class TestHealthEndpoint:
    """Test suite for /health endpoint"""
    
    def test_health_check_success(self, client):
        """Test successful health check"""
        response = client.get("/health")
        
        assert response.status_code == status.HTTP_200_OK
        
        data = response.json()
        assert "status" in data
        assert data["status"] == "healthy"
        assert "timestamp" in data
        assert "version" in data
        assert "environment" in data
    
    def test_health_check_response_structure(self, client):
        """Test health check response structure"""
        response = client.get("/health")
        data = response.json()
        
        # Verify all required fields are present
        required_fields = ["status", "timestamp", "version", "environment"]
        for field in required_fields:
            assert field in data, f"Field '{field}' is missing from response"
    
    def test_health_check_timestamp_format(self, client):
        """Test health check timestamp is valid ISO format"""
        response = client.get("/health")
        data = response.json()
        
        # Verify timestamp can be parsed
        timestamp = data["timestamp"]
        try:
            datetime.fromisoformat(timestamp.replace('Z', '+00:00'))
        except ValueError:
            pytest.fail(f"Invalid timestamp format: {timestamp}")
    
    def test_health_check_version(self, client):
        """Test health check returns correct version"""
        response = client.get("/health")
        data = response.json()
        
        assert data["version"] == "1.0.0"
    
    def test_health_check_headers(self, client):
        """Test health check response headers"""
        response = client.get("/health")
        
        # Check for custom process time header added by middleware
        assert "X-Process-Time" in response.headers
        
        # Verify process time is a valid float
        process_time = float(response.headers["X-Process-Time"])
        assert process_time >= 0





