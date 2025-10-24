"""Application configuration settings"""
import os
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """Application settings"""
    
    # Application metadata
    app_name: str = "Demo Service"
    app_description: str = "A demo FastAPI service with best practices"
    app_version: str = "1.0.0"
    
    # Environment configuration
    environment: str = os.getenv("ENVIRONMENT", "development")
    debug: bool = os.getenv("DEBUG", "true").lower() == "true"
    
    # Logging configuration
    log_level: str = os.getenv("LOG_LEVEL", "INFO")
    
    class Config:
        """Pydantic configuration"""
        env_file = ".env"
        case_sensitive = False


# Create settings instance
settings = Settings()




