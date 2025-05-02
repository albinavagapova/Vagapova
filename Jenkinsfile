pipeline {
    agent {
        docker {
            image 'maven'
        }
    }
    stages {
        stage("Git") {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/albinavagapova/FinishAttestationVagapova']]) // скачать проект
            }
        }
        stage("Run tests") {
            steps {
                sh 'mvn clean test'
            }
        }
    }
    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
        }
    }
}