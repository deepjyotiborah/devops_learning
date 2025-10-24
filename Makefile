.PHONY: help install run test clean lint format

help:
	@echo "Available commands:"
	@echo "  make install    - Install dependencies"
	@echo "  make run        - Run the application"
	@echo "  make test       - Run tests"
	@echo "  make clean      - Clean up cache and temporary files"
	@echo "  make lint       - Check code style"
	@echo "  make format     - Format code"

install:
	pip install -r requirements.txt

run:
	python -m app.main

dev:
	uvicorn app.main:app --reload --host 0.0.0.0 --port 8000

test:
	pytest -v

test-cov:
	pytest --cov=app --cov-report=html --cov-report=term

clean:
	find . -type d -name "__pycache__" -exec rm -rf {} +
	find . -type f -name "*.pyc" -delete
	find . -type f -name "*.pyo" -delete
	find . -type d -name "*.egg-info" -exec rm -rf {} +
	find . -type d -name ".pytest_cache" -exec rm -rf {} +
	find . -type d -name ".coverage" -exec rm -rf {} +
	find . -type d -name "htmlcov" -exec rm -rf {} +

lint:
	@echo "Running flake8..."
	-flake8 app tests
	@echo "Running mypy..."
	-mypy app

format:
	@echo "Formatting with black..."
	-black app tests
	@echo "Sorting imports with isort..."
	-isort app tests





