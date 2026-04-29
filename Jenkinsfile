pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    parameters {
        choice(
            name: 'TEST_TAG',
            choices: [
                '@smoke',
                '@regression',
                '@home',
                '@cart',
                '@featured',
                '@search',
                '@checkout',
                '@guestUser',
                '@existingUser',
                '@registerUser'
            ],
            description: 'Select Cucumber tag to execute'
        )
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Clean Project') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn test -Dcucumber.filter.tags=${params.TEST_TAG}"
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/**/*', allowEmptyArchive: true
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }

        success {
            echo 'Tests executed successfully.'
        }

        failure {
            echo 'Tests failed. Check console output and reports.'
        }
    }
}