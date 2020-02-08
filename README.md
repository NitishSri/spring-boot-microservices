# SpringApplication

This is a Spring Boot microservice based Login and Registration application. The forgot password functionality sends email with queue in between. If send-email application is down, all emails will be preserved in queue (rabbit mq used). Once the application is up, mails will be sent by a third party integration of mailjet.

Technology Stack Used :

Spring Boot (Login , Registration , Send Email microservice)
Eureka Server
Spring Cloud Config Server
JUnit , Mockito
Swagger
Hystrix
MySQL db (As relation database)
Feign (To call one microservice to another)
Zuul APi Gateway
Rabbit MQ to queue message
Zipkin (For distributed tracing)
Mailjet (For sending email)
Docker (Dockerized all container using compose)
