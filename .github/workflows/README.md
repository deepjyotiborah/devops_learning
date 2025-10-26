# GitHub Actions CI/CD Workflows

This directory contains GitHub Actions workflows for automating CI/CD processes.

## Workflows

### `main.yaml` - CI Pipeline

**Triggered on:**
- Push to `main`, `develop`, or `feature/**` branches
- Pull requests to `main` or `develop` branches

**Jobs:**

#### 1. **Test Job**
Runs comprehensive tests across multiple Python versions.

**Features:**
- Tests on Python 3.10, 3.11, 3.12, and 3.13
- Checks out repository
- Sets up Python with pip caching
- Installs dependencies
- Runs pytest with:
  - Verbose output
  - JUnit XML test results
  - Code coverage (XML and HTML reports)
- Uploads test results and coverage reports as artifacts
- Displays test summary in GitHub UI

**Artifacts:**
- `test-results-{python-version}.xml` - JUnit test results (30 days retention)
- `coverage-report-{python-version}` - Coverage reports (30 days retention)

#### 2. **Lint Job**
Checks code quality and formatting.

**Checks:**
- Flake8 for syntax errors and code quality
- Black for code formatting
- isort for import sorting

#### 3. **Build Summary Job**
Generates a summary of all CI jobs.

**Features:**
- Runs after test and lint jobs complete
- Shows status of all jobs
- Displays commit and branch information

## Usage

### Viewing Test Results

After a workflow run:

1. Go to the **Actions** tab in your GitHub repository
2. Click on the workflow run
3. View the test summary in the job summary
4. Download artifacts to see detailed test reports

### Artifacts

Download test results and coverage reports:

```bash
# From GitHub UI: Actions → Workflow Run → Artifacts section
# Or use GitHub CLI:
gh run download <run-id>
```

### Local Testing

Test the workflow locally before pushing:

```bash
# Run tests locally
python3 -m pytest -v --junitxml=test-results.xml --cov=app --cov-report=html

# Check code quality
flake8 app tests
black --check app tests
isort --check-only app tests
```

## Configuration

### Python Versions

To modify tested Python versions, edit the matrix in `main.yaml`:

```yaml
strategy:
  matrix:
    python-version: ['3.10', '3.11', '3.12', '3.13']
```

### Branch Triggers

To change which branches trigger the workflow:

```yaml
on:
  push:
    branches:
      - main
      - your-branch-name
```

### Artifact Retention

To change how long artifacts are kept (default 30 days):

```yaml
- name: Upload test results
  uses: actions/upload-artifact@v4
  with:
    retention-days: 30  # Change this value
```

## Advanced Features

### Matrix Strategy

The workflow uses GitHub Actions matrix strategy to test across multiple Python versions efficiently, running tests in parallel.

### Caching

Python dependencies are cached to speed up workflow runs:

```yaml
- uses: actions/setup-python@v5
  with:
    cache: 'pip'  # Caches pip dependencies
```

### Conditional Steps

Steps run conditionally based on previous step results:

```yaml
if: always()  # Run even if previous steps fail
```

## Troubleshooting

### Workflow Not Triggering

**Check:**
1. Branch name matches trigger patterns
2. Workflow file is in `.github/workflows/`
3. YAML syntax is valid

### Test Failures

**Check:**
1. All dependencies are in `requirements.txt`
2. Tests pass locally: `pytest -v`
3. Python version compatibility

### Artifact Upload Issues

**Check:**
1. File paths are correct
2. Files exist after test run
3. Artifact names are unique

## Security

### Permissions

The workflow uses minimal required permissions. Add permissions as needed:

```yaml
permissions:
  contents: read
  id-token: write
```

## Best Practices

1. **Always test locally** before pushing
2. **Use caching** to speed up workflows
3. **Set appropriate retention** for artifacts
4. **Use matrix strategy** for multi-version testing
5. **Add status badges** to README
6. **Review workflow runs** regularly

## Status Badge

Add this to your README to show CI status:

```markdown
![CI Pipeline](https://github.com/YOUR_USERNAME/demoService/actions/workflows/main.yaml/badge.svg)
```

## Additional Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Workflow Syntax](https://docs.github.com/en/actions/reference/workflow-syntax-for-github-actions)
- [Python Setup Action](https://github.com/actions/setup-python)
- [Upload Artifact Action](https://github.com/actions/upload-artifact)

---

**Note**: Make sure to push the `.github/workflows/` directory to your repository for workflows to run.

