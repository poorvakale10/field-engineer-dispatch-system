
param(
    [string]$PreviousImage = "dispatchflow:previous"
)

$ErrorActionPreference = "Stop"

Write-Host "Checking previous Docker image..."

$imageExists = docker image inspect $PreviousImage 2>$null
if ($LASTEXITCODE -ne 0) {
    Write-Host "Rollback image '$PreviousImage' is not available."
    Write-Host "No containers have been changed."
    exit 1
}

Write-Host "Previous image found. Starting rollback container..."

docker rm -f dispatchflow-rollback 2>$null | Out-Null
docker run -d --name dispatchflow-rollback -p 8082:8080 $PreviousImage

if ($LASTEXITCODE -ne 0) {
    throw "Failed to start the rollback container."
}

Write-Host "Check the previous version at http://localhost:8082"
