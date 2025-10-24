"""API routes for the application"""
import logging
from datetime import datetime
from fastapi import APIRouter, HTTPException, status
from app.models import HealthCheckResponse, ErrorResponse
from app.config import settings
from app.exceptions import ResourceNotFoundError, ServiceUnavailableError

logger = logging.getLogger(__name__)

router = APIRouter()


@router.get(
    "/health",
    response_model=HealthCheckResponse,
    status_code=status.HTTP_200_OK,
    tags=["Health"],
    summary="Health check endpoint",
    description="Returns the health status of the service along with metadata",
    responses={
        200: {
            "description": "Service is healthy",
            "model": HealthCheckResponse
        },
        503: {
            "description": "Service unavailable",
            "model": ErrorResponse
        }
    }
)
async def health_check():
    """
    Health check endpoint to verify service availability.
    
    Returns:
        HealthCheckResponse: Contains service status, timestamp, version, and environment
    
    Raises:
        HTTPException: If service is unavailable (503)
    """
    try:
        logger.info("Health check requested")
        
        return HealthCheckResponse(
            status="healthy",
            timestamp=datetime.utcnow(),
            version=settings.app_version,
            environment=settings.environment
        )
    except Exception as e:
        logger.error(f"Health check failed: {str(e)}")
        raise HTTPException(
            status_code=status.HTTP_503_SERVICE_UNAVAILABLE,
            detail="Service unavailable"
        )


@router.get(
    "/",
    tags=["Root"],
    summary="Root endpoint",
    description="Returns basic API information",
    status_code=status.HTTP_200_OK
)
async def root():
    """
    Root endpoint providing basic API information.
    
    Returns:
        dict: API name, version, and documentation URL
    """
    return {
        "name": settings.app_name,
        "version": settings.app_version,
        "description": settings.app_description,
        "docs_url": "/docs",
        "health_url": "/health"
    }





