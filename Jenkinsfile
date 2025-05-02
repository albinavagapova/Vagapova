pipeline {
    agent {
        docker {
            image 'maven'
        }
    }
    stages {
        stage("Checkout Repository") {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: 'main']], userRemoteConfigs: [[url: 'https://github.com/albinavagapova/FinishAttestationVagapova']]])
            }
        }
        stage("Run tests") {
            steps {
                sh 'mvn clean test'
            }
        }
        stage("Generate Allure Report") {
            steps {
                sh 'mvn allure:report'
            }
        }
    }
    post {
        always {
            node {
                allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
            }
        }
    }
}
