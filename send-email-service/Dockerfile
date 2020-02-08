FROM openjdk:8-jdk-alpine
MAINTAINER Nitish Srivastava
VOLUME /tmp
EXPOSE 8067
ADD target/*.jar app.jar
ENTRYPOINT ["sh","-c","java -jar /app.jar"]