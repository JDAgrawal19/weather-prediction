pipeline {
    environment {
        registry = "nikator_19/weatherprediction"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }

    agent any

    stages {

        stage('SC checkout') {
            steps {
                echo 'checking out'
                 git  url: 'https://github.com/JDAgrawal19/weather-prediction.git', branch: 'main'
            }
        }

        stage('build install')
                {
                    steps {
                        echo 'h2'
                        sh 'mvn clean package -Dmaven.test.skip'
                    }
                }


        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build(registry + ":$BUILD_NUMBER",'-f ./deploy/docker/Dockerfile .')
                }
            }
        }
        stage('Push our image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Deployment') {
            steps{
                    sh "docker run -d -p 8080:8080 weather registry:$BUILD_NUMBER"
            }
        }

        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}

