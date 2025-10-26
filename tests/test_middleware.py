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
    
    def test_logging_middleware_configured(self, client):
        """Test that logging middleware is configured and working"""
        # Verify logging middleware is working by checking X-Process-Time header
        response = client.get("/health")
        
        # The presence of X-Process-Time header confirms our custom middleware is working
        assert "X-Process-Time" in response.headers
        assert response.status_code == 200





