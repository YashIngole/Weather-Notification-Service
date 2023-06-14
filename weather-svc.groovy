pipeline {
    agent any

    stages {
        stage ('getting the project from git'){
            steps {
                echo 'cloning git repo'
                sh 'git clone https://github.com/YashIngole/Weather-Notification-Service'
                sh 'cd Weather-Notification-Service'

            }
        }
        stage ('running docker'){
            steps{
                 def dockerImage = docker.build('springimagev6')
                 sh 'docker tag cc6ab1b51c68 asia-south1-docker.pkg.dev/smart-bridge-383909/springimage3/springimagev10'
                 sh 'docker push asia-south1-docker.pkg.dev/smart-bridge-383909/springimage3/springimagev10:latest '

            }
        }
        stage ('deploy to cloud run ')
        steps {
            sh 'gcloud run deploy springimagev2 --image gcr.io/your-project-id/your-image-name:your-tag-name --platform managed --region your-region'
        }
    }
}//check