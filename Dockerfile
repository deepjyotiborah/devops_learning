# Use official Python runtime as base image
FROM python:3.11-slim

# Set environment variables
# Prevents Python from writing pyc files to disc (equivalent to python -B)
ENV PYTHONDONTWRITEBYTECODE=1
# Prevents Python from buffering stdout and stderr (equivalent to python -u)
ENV PYTHONUNBUFFERED=1
# Set working directory
ENV APP_HOME=/app

# Set working directory
WORKDIR $APP_HOME

# Create a non-root user for security
RUN groupadd -r appuser && \
    useradd -r -g appuser -u 1001 appuser && \
    chown -R appuser:appuser $APP_HOME

# Install system dependencies (if needed)
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    gcc \
    && rm -rf /var/lib/apt/lists/*

# Copy requirements file
COPY --chown=appuser:appuser requirements.txt .

# Install Python dependencies
RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

# Copy application code
COPY --chown=appuser:appuser . .

# Switch to non-root user
USER appuser

# Expose the port the app runs on
EXPOSE 8000

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD python -c "import urllib.request; urllib.request.urlopen('http://localhost:8000/health')" || exit 1

# Define the entry point for the application
# Use uvicorn with multiple workers for production
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000", "--workers", "4"]

