FROM openjdk:8-jdk-alpine
MAINTAINER Nitish Srivastava
ADD /target/Docker-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8069
ENTRYPOINT ["sh","-c", "java -jar /app.jar"]