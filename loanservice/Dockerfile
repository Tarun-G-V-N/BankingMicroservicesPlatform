FROM openjdk:17-jdk-alpine

RUN apk add --no-cache curl grep

COPY target/dependency/opentelemetry-javaagent-2.11.0.jar app/libs/opentelemetry-javaagent-2.11.0.jar

COPY target/loanservice-0.0.1-SNAPSHOT.jar loanservice-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/loanservice-0.0.1-SNAPSHOT.jar"]