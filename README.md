# Weather Notification Service Documentation

Introduction

This documentation provides an overview of the Java Web Spring Boot application. The application is responsible for fetching weather data for cities like Nagpur, Pune, Mumbai, Bangalore, Chennai, and Kolkata. It periodically sends email notifications to recipients containing weather parameters such as temperature, wind speed, and humidity for these cities. The application is containerized using Docker and deployed on Google Cloud Run for easy scalability and accessibility.

Architecture Overview

The architecture of application involves several components working together to achieve the desired functionality. The key components are as follows:
1.	Java Web Spring Boot Application: This is the core of application, responsible for fetching weather data and generating email notifications in HTML format.
2.	Docker: The application is containerized using Docker, which allows for easy deployment and scalability. The Docker image is built using the docker build command and pushed to the Artifact Registry of Google Cloud using the docker push command.
3.	Google Cloud Run: The Docker image is deployed on Google Cloud Run, a fully managed serverless platform. Cloud Run recognizes the Docker image in the Artifact Registry and deploys it, generating a unique URL for accessing the application.
4.	Cloud Scheduler: To schedule the periodic execution of application, I utilized the Google Cloud Scheduler. A scheduled job is set up to hit the application's URL at regular intervals (every one hour in this case).
5.	Continuous Deployment with Cloud Build: To enable continuous deployment, Cloud Build is integrated with Git repository. Whenever we push new code to the repository, Cloud Build triggers a new build, which pulls the latest code, builds a new Docker image, and deploys it on Cloud Run.


Deployment Steps

1.	Build Docker Image:
•	Execute the docker build command to build a Docker image for application:

docker build -t springimage .

2.	Push Docker Image to Artifact Registry:
•	Tag the Docker image with the appropriate registry location using the docker tag command:
•	
docker tag cc6ab1b51c68 asia-south1-docker.pkg.dev/<your project id>/springimage3/springimagev10

•	Push the Docker image to the Artifact Registry on Google Cloud using the docker push command:

docker push asia-south1-docker.pkg.dev/<your project id>/springimage3/springimagev10:latest

3.	Deploy Docker Image on Cloud Run:
•	Access the Google Cloud Console and navigate to the Cloud Run section.
•	Create a new service and specify the previously pushed Docker image as the deployment source.
•	Cloud Run will automatically deploy the image and generate a unique URL for application, such as:

 https://springimagev10.a.run.app.

4.	Schedule Execution with Cloud Scheduler:

•	Access the Google Cloud Console and navigate to the Cloud Scheduler section.
•	Create a new job and set the target URL to the URL generated by Cloud Run

 (https://springimagev10.a.run.app/weather).

•	Configure the schedule to execute the job every one hour.


5.	Enable Continuous Deployment with Cloud Build:

•	Access the Google Cloud Console and navigate to the Cloud Build section.
•	Configure a trigger to watch Git repository for new code pushes.
•	Specify the build steps required to build the Docker image and deploy it on Cloud Run.



