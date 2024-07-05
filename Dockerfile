# Etapa de construção
FROM ubuntu:latest AS builder

# Atualizar os pacotes e instalar o JDK e o Maven
RUN apt-get update && \
    apt-get install -y openjdk-22-jdk maven

# Copiar o código fonte para o contêiner
COPY . .

# Construir o projeto usando Maven
RUN mvn clean install

# Etapa final
FROM openjdk:22-jdk-slim

# Expor a porta 8082
EXPOSE 8082

# Copiar o JAR construído do estágio de construção para o estágio final
COPY --from=builder /target/springboot-project-0.0.1-SNAPSHOT.jar app.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT [ "java", "-jar", "app.jar" ]
