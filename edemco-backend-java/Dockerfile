# Etapa 1: Build
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app

# Copiamos el pom.xml primero para aprovechar el caché de Docker
COPY pom.xml .

# Ahora copiamos el resto de los archivos
COPY . .

# Instalamos las dependencias y construimos el proyecto
RUN mvn install -DskipTests

