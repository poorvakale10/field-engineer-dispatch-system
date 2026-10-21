
pipeline {
    agent any

    environment {
    MAVEN_HOME = 'C:\\Users\\Poorva\\Downloads\\apache-maven-3.10.0'
    DOCKER_PATH = 'C:\\Users\\Poorva\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin'
    IMAGE = 'dispatchflow:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                bat 'call "%MAVEN_HOME%\\bin\\mvn.cmd" -B clean verify'
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                bat '''
                    "%DOCKER_PATH%\\docker.exe" --version
                    "%DOCKER_PATH%\\docker.exe" build --no-cache -t %IMAGE% .
                '''
            }
        }

        
        stage('Deploy') {
            steps {
                bat '''
                    "%DOCKER_PATH%\\docker.exe" rm -f dispatchflow 2>NUL
                    "%DOCKER_PATH%\\docker.exe" run -d --name dispatchflow -p 8081:8080 %IMAGE%
                    if errorlevel 1 exit /b 1
                '''
            }
        }


        stage('Health Check') {
            steps {
                powershell '''
                    $healthy = $false

                    for ($i = 0; $i -lt 12; $i++) {
                        try {
                            $response = Invoke-WebRequest `
                                -UseBasicParsing `
                                -Uri "http://localhost:8081/" `
                                -TimeoutSec 5

                            if ($response.StatusCode -ge 200 -and
                                $response.StatusCode -lt 400) {
                                $healthy = $true
                                break
                            }
                        } catch {
                            Start-Sleep -Seconds 5
                        }
                    }

                    if (-not $healthy) {
                        docker logs dispatchflow
                        throw "Health check failed: application did not respond successfully."
                    }

                    Write-Host "SUCCESS: DispatchFlow is responding on port 8081."
                '''
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: Build, tests, Docker deployment, and health check completed.'
        }
        failure {
            echo 'FAILED: Check the first failed stage in Console Output.'
        }
    }
}
