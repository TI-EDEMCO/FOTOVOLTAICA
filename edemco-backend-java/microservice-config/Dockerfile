# Etapa 1: Build
FROM micro-pom AS build
WORKDIR /app
# Copiamos el pom.xml y las dependencias primero para aprovechar el caché de Docker
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline -B
# Copiamos el resto del proyecto y construimos
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/microservice-config-0.0.1-SNAPSHOT.jar .
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "microservice-config-0.0.1-SNAPSHOT.jar"]
