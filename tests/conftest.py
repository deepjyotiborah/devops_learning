"""Pytest configuration and fixtures"""
import pytest
from fastapi.testclient import TestClient
from app.main import app


@pytest.fixture
def client():
    """Create a test client for the FastAPI application"""
    return TestClient(app)


@pytest.fixture
def mock_settings():
    """Mock settings for testing"""
    return {
        "app_name": "DemoService",
        "app_version": "1.0.0",
        "app_description": "A production-ready FastAPI demo service",
        "environment": "development",
        "debug": True,
        "log_level": "INFO"
    }





