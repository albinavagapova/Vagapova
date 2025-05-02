pipeline {
    agent {
        docker {
            image 'maven'
        }
    }
    stages {
        stage("Git Checkout") {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], userRemoteConfigs: [[url: 'https://github.com/albinavagapova/FinishAttestationVagapova']]])
            }
        }
        stage("Run Tests") {
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
            allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
        }
    }
}
