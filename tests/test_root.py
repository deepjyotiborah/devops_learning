"""Unit tests for root endpoint"""
from fastapi import status


class TestRootEndpoint:
    """Test suite for / endpoint"""
    
    def test_root_endpoint_success(self, client):
        """Test root endpoint returns successfully"""
        response = client.get("/")
        
        assert response.status_code == status.HTTP_200_OK
    
    def test_root_endpoint_response_structure(self, client):
        """Test root endpoint response structure"""
        response = client.get("/")
        data = response.json()
        
        # Verify required fields
        required_fields = ["name", "version", "description", "docs_url", "health_url"]
        for field in required_fields:
            assert field in data, f"Field '{field}' is missing from response"
    
    def test_root_endpoint_content(self, client):
        """Test root endpoint content"""
        response = client.get("/")
        data = response.json()
        
        assert data["name"] == "Demo Service"
        assert data["version"] == "1.0.0"
        assert data["docs_url"] == "/docs"
        assert data["health_url"] == "/health"
        assert "description" in data





