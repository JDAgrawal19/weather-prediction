pipeline {
    environment {
        registry = "nikator_19/weatherprediction"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }

    agent any // { dockerfile true }

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {

        stage('SC checkout') {
            steps {
                echo 'checking out'
                git 'https://github.com/JDAgrawal19/weather-prediction.git'
            }
        }

        stage('build install')
                {
                    steps {
                        echo 'h2'
                        // withEnv( ["PATH+MAVEN=${tool 'M3'}/bin"] ) {
                        sh 'mvn clean package -Dmaven.test.skip'
                        //}
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

