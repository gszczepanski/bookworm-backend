FROM openjdk:11-jre-slim as builder
WORKDIR bookworm
ARG JAR_FILE=bookworm-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bookworm.jar
RUN java -Djarmode=layertools -jar bookworm.jar extract

FROM openjdk:11-jre-slim
RUN mkdir /bookworm \
    && adduser --group spring-users \
    && adduser --gecos "First Last,RoomNumber,WorkPhone,HomePhone" --disabled-password --disabled-login spring \
    && adduser spring spring-users
USER spring:spring-users
WORKDIR bookworm
COPY --from=builder bookworm/dependencies/ ./
COPY --from=builder bookworm/spring-boot-loader/ ./
COPY --from=builder bookworm/snapshot-dependencies/ ./
COPY --from=builder bookworm/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
