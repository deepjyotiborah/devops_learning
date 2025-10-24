# Quick Start Guide

Get up and running with DemoService in 5 minutes.

## 1. Setup Environment

```bash
# Create virtual environment
python -m venv venv

# Activate virtual environment
source venv/bin/activate  # macOS/Linux
# or
venv\Scripts\activate  # Windows
```

## 2. Install Dependencies

```bash
pip install -r requirements.txt
```

## 3. Run the Service

```bash
# Option 1: Direct Python
python -m app.main

# Option 2: Using uvicorn
uvicorn app.main:app --reload

# Option 3: Using make
make run
```

## 4. Test the API

```bash
# Health check
curl http://localhost:8000/health

# Root endpoint
curl http://localhost:8000/

# Interactive docs
# Open browser: http://localhost:8000/docs
```

## 5. Run Tests

```bash
# Run all tests
pytest

# With coverage
pytest --cov=app --cov-report=html

# Using make
make test
```

## That's It!

Your FastAPI service is now running at http://localhost:8000

**Next Steps:**
- Visit http://localhost:8000/docs for interactive API documentation
- Read [README.md](README.md) for detailed information
- Check [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for complete API docs

## Common Commands

```bash
make help          # Show all available commands
make install       # Install dependencies
make run           # Run the application
make test          # Run tests
make test-cov      # Run tests with coverage
make clean         # Clean up cache files
```

## Troubleshooting

**Port already in use?**
```bash
lsof -i :8000      # Find process
kill -9 <PID>      # Kill process
```

**Import errors?**
```bash
# Make sure you're in project root
pwd

# Make sure virtual environment is activated
which python
```

**Need help?**
- Check the logs in your terminal
- Visit http://localhost:8000/docs for API documentation
- Read the full README.md





