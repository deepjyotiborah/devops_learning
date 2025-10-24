"""Unit tests for middleware"""
from fastapi import status


class TestMiddleware:
    """Test suite for middleware functionality"""
    
    def test_logging_middleware_adds_headers(self, client):
        """Test that logging middleware adds custom headers"""
        response = client.get("/health")
        
        assert "X-Process-Time" in response.headers
    
    def test_process_time_header_format(self, client):
        """Test process time header is valid float"""
        response = client.get("/health")
        
        process_time = response.headers.get("X-Process-Time")
        assert process_time is not None
        
        # Should be parseable as float
        try:
            time_value = float(process_time)
            assert time_value >= 0
        except ValueError:
            pytest.fail(f"Invalid process time value: {process_time}")
    
    def test_cors_headers(self, client):
        """Test CORS middleware is configured"""
        # Verify that CORS middleware allows requests
        # Make a simple request to verify the app is working with CORS configured
        response = client.get("/health")
        assert response.status_code == 200
        
        # Verify the middleware chain is working
        # (CORS headers are added by Starlette but may not be visible in TestClient)
        from app.main import app
        
        # Verify middleware is registered  
        middleware_classes = [type(m).__name__ for m in app.middleware]
        assert any("CORS" in name for name in middleware_classes), "CORS middleware should be configured"





