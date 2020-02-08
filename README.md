# SpringApplication

This is Spring Boot microservice based Login and Registration application. The functionality of forgot password send email with queue in between.
If email send application is down, all emails will be preserved in queue (rabbit mq used). Once the application is up, mails will be send
by a third party mailjet.

Technology Stack Used :

Spring Boot (Login , Registration , Send Email microservice)
Spring Cloud Config Server
MySQL db (As relation database)
Feign (To call one microservice to another)
Zuul APi Gateway
Rabbit MQ to queue message
Zipkin (For distributed tracing)
Mailjet (For sending email)
Docker (Dockerized all container using compose)
