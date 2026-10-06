pipeline {
  agent any
  parameters { string(name:'DEPLOY_ENV', defaultValue:'local', description:'Deployment environment') }
  environment { IMAGE="poorvakale/dispatchflow:${BUILD_NUMBER}" }
  stages {
    stage('Checkout'){steps{checkout scm}}
    stage('Build'){steps{sh 'mvn -B -DskipTests package'}}
    stage('Test'){steps{sh 'mvn -B test'} post{always{junit allowEmptyResults:true,testResults:'target/surefire-reports/*.xml'}}}
    stage('Package'){steps{archiveArtifacts artifacts:'target/*.jar',fingerprint:true}}
    stage('Docker Build'){steps{sh 'docker build -t ${IMAGE} .'}}
    stage('Deploy'){steps{sh 'docker rm -f dispatchflow || true; docker run -d --name dispatchflow -p 8080:8080 ${IMAGE}'}}
    stage('Health Check'){steps{sh 'curl --fail http://localhost:8080/health'}}
  }
}
