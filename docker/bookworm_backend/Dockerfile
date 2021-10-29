FROM openjdk:11-jre-slim
MAINTAINER grzegorz
RUN mkdir /bookworm && adduser --group spring-users && adduser --disabled-password --disabled-login spring && adduser spring spring-users
USER spring:spring-users
ARG JAR_FILE=./bookworm-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /bookworm
ENTRYPOINT ["java","-jar","/bookworm/bookworm-backend-0.0.1-SNAPSHOT.jar"]
