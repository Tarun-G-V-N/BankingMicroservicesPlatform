FROM openjdk:17-jdk-alpine

RUN apk add --no-cache curl grep

COPY target/dependency/opentelemetry-javaagent-2.11.0.jar app/libs/opentelemetry-javaagent-2.11.0.jar

COPY target/messageservice-0.0.1-SNAPSHOT.jar messageservice-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/messageservice-0.0.1-SNAPSHOT.jar"]