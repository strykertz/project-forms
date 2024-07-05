FROM ubuntu:latest AS builder

RUN apt-get update
RUN apt-get install openjdk-22 -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:22-jdk-slim

EXPOSE 8082

COPY --from=builder /target/springboot-project-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]