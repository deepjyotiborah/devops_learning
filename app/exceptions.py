"""Custom exception classes for the application"""


class DemoServiceException(Exception):
    """Base exception for all demo service errors"""
    
    def __init__(self, message: str = "An error occurred", detail: str = None):
        self.message = message
        self.detail = detail
        super().__init__(self.message)


class ServiceUnavailableError(DemoServiceException):
    """Exception raised when the service is unavailable"""
    
    def __init__(self, message: str = "Service is currently unavailable", detail: str = None):
        super().__init__(message, detail)


class ResourceNotFoundError(DemoServiceException):
    """Exception raised when a requested resource is not found"""
    
    def __init__(self, message: str = "Resource not found", detail: str = None):
        super().__init__(message, detail)


class ValidationError(DemoServiceException):
    """Exception raised when validation fails"""
    
    def __init__(self, message: str = "Validation failed", detail: str = None):
        super().__init__(message, detail)




