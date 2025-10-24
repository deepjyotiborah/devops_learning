"""Unit tests for error handling"""
from fastapi import status


class TestErrorHandling:
    """Test suite for error handling"""
    
    def test_404_not_found(self, client):
        """Test 404 error handling for non-existent endpoint"""
        response = client.get("/nonexistent")
        
        assert response.status_code == status.HTTP_404_NOT_FOUND
        
        data = response.json()
        assert "error" in data
        assert data["error"] == "NotFoundError"
        assert "message" in data
        assert "detail" in data
    
    def test_404_error_structure(self, client):
        """Test 404 error response structure"""
        response = client.get("/invalid/path")
        data = response.json()
        
        # Verify error response structure
        required_fields = ["error", "message", "detail"]
        for field in required_fields:
            assert field in data, f"Field '{field}' is missing from error response"
    
    def test_method_not_allowed(self, client):
        """Test method not allowed error"""
        # Assuming /health only supports GET
        response = client.post("/health")
        
        assert response.status_code == status.HTTP_405_METHOD_NOT_ALLOWED
    
    def test_invalid_endpoint_formats(self, client):
        """Test various invalid endpoint formats"""
        invalid_paths = [
            "/api/nonexistent",
            "/health/extra",
            "/does/not/exist",
        ]
        
        for path in invalid_paths:
            response = client.get(path)
            assert response.status_code == status.HTTP_404_NOT_FOUND
            data = response.json()
            assert "error" in data





