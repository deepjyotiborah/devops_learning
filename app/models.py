"""Pydantic models for request/response schemas"""
from datetime import datetime
from typing import Optional
from pydantic import BaseModel, Field


class HealthCheckResponse(BaseModel):
    """Health check response model"""
    
    status: str = Field(..., description="Health status of the service")
    timestamp: datetime = Field(..., description="Current timestamp")
    version: str = Field(..., description="Application version")
    environment: str = Field(..., description="Current environment")
    
    class Config:
        """Pydantic configuration"""
        json_schema_extra = {
            "example": {
                "status": "healthy",
                "timestamp": "2025-10-17T10:30:00",
                "version": "1.0.0",
                "environment": "development"
            }
        }


class ErrorResponse(BaseModel):
    """Error response model"""
    
    error: str = Field(..., description="Error type")
    message: str = Field(..., description="Error message")
    detail: Optional[str] = Field(None, description="Additional error details")
    
    class Config:
        """Pydantic configuration"""
        json_schema_extra = {
            "example": {
                "error": "ServiceUnavailableError",
                "message": "Service is currently unavailable",
                "detail": "Database connection failed"
            }
        }




