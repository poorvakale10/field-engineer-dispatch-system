
pipeline {
    agent any

    parameters {
        string(
            name: 'DEPLOY_ENV',
            defaultValue: 'local',
            description: 'Deployment environment'
        )
    }

    environment {
        IMAGE = "poorvakale/dispatchflow:${BUILD_NUMBER}"
        MVN = 'C:\\Users\\Poorva\\Downloads\\apache-maven-3.10.0\\bin\\mvn.cmd'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'call "%MVN%" -B -DskipTests package'
            }
        }

        stage('Test') {
            steps {
                bat 'call "%MVN%" -B test'
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

                            if ($response.StatusCode -eq 200) {
                                $healthy = $true
                                break
                            }
                        } catch {
                            Start-Sleep -Seconds 5
                        }
                    }

                    if (-not $healthy) {
                        docker logs dispatchflow
                        throw "DispatchFlow health check failed"
                    }

                    Write-Host "DispatchFlow is responding successfully."
                '''
            }
        }
    }

    post {
        success {
            echo 'Build, tests, Docker deployment, and health check succeeded.'
        }
        failure {
            echo 'Pipeline failed. Review the stage logs for details.'
        }
    }
}
