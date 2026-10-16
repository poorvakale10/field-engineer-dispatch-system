
pipeline {
    agent any

    environment {
        MAVEN_HOME = 'C:\\Users\\Poorva\\Downloads\\apache-maven-3.10.0'
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
                bat 'docker build -t %IMAGE% .'
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                    docker rm -f dispatchflow 2>NUL
                    docker run -d --name dispatchflow -p 8081:8080 %IMAGE%
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
                        throw "Health check failed"
                    }

                    Write-Host "Application is responding."
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check the stage logs.'
        }
    }
}
