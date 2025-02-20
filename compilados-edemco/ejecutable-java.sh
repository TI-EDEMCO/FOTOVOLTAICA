#!/bin/bash

# Lista ordenada de microservicios y sus puertos/endpoints de health check
services=(
    "microservice-config-0.0.1-SNAPSHOT.jar|http://localhost:8888/actuator/health|8888"
    "microservice-eureka-0.0.1-SNAPSHOT.jar|http://localhost:8761/eureka/health|8761"
    "microservice-gateway-0.0.1-SNAPSHOT.jar|http://localhost:8080/actuator/health|8080"
    "microservice-factura-0.0.1-SNAPSHOT.jar|http://localhost:8060/actuator/health|8060"
    "microservice-facturacion-especial-0.0.1-SNAPSHOT.jar|http://localhost:9081/actuator/health|9081"
    "microservice-remitentes-0.0.1-SNAPSHOT.jar|http://localhost:8070/actuator/health|8070"
    "microservice-security-0.0.1-SNAPSHOT.jar|http://localhost:8050/actuator/health|8050"
    "microservice.generation-0.0.1-SNAPSHOT.jar|http://localhost:9092/actuator/health|9092"
    "Operadores-0.0.1-SNAPSHOT.jar|http://localhost:9091/actuator/health|9091"
    "IntegracionSiesa-0.0.1-SNAPSHOT.jar|http://localhost:9090/actuator/health|9090"
)

# Función para matar cualquier proceso que esté usando el nombre del archivo JAR
function kill_by_jar() {
    local jar="$1"
    pid=$(ps aux | grep "$jar" | grep -v "grep" | awk '{print $2}')
    if [[ -n "$pid" ]]; then
        echo "Matando proceso del microservicio $jar (PID: $pid)"
        kill -9 $pid
    else
        echo "No se encontró ningún proceso para $jar"
    fi
}

# Función para matar procesos que usan un puerto específico
function kill_by_port() {
    local port="$1"
    # Encuentra el primer PID que está usando el puerto y lo mata
    pid=$(netstat -ano | grep ":$port " | awk '{print $5}' | head -n 1)
    if [[ -n "$pid" ]]; then
        echo "Matando proceso que usa el puerto $port (PID: $pid)"
        taskkill //PID "$pid" //F  # Asegurarse de que $pid esté correctamente entre comillas
    else
        echo "No se encontró ningún proceso en el puerto $port."
    fi
}

# Función para comprobar si el servicio está listo
function wait_for_service() {
    local url="$1"
    local timeout=120  # Tiempo máximo de espera
    local elapsed=0
    local interval=5

    while [[ $elapsed -lt $timeout ]]; do
        if curl -s "$url" > /dev/null; then
            echo "El servicio en $url está listo."
            return 0
        fi
        sleep $interval
        ((elapsed += interval))
    done

    echo "El servicio en $url no está listo después de $timeout segundos."
    return 1
}

# Eliminar archivos de log antes de iniciar
function clean_logs() {
    echo "Limpiando archivos de log antiguos..."
    rm -f *.log
    echo "Archivos de log eliminados."
}

# Función para limpiar los procesos al detener el script
function cleanup() {
    echo "Deteniendo todos los microservicios y liberando puertos..."
    for service in "${services[@]}"; do
        jar=$(echo $service | cut -d'|' -f1)
        port=$(echo $service | cut -d'|' -f3)

        kill_by_jar "$jar"
        kill_by_port "$port"
    done
    echo "Todos los microservicios detenidos y puertos liberados."
    exit 0
}

# Capturar la señal de interrupción (Ctrl+C) para matar los procesos
trap cleanup SIGINT

# Limpiar logs antiguos
clean_logs

# Ejecutar cada microservicio en el orden definido y esperar a que esté listo
for service in "${services[@]}"; do
    jar=$(echo $service | cut -d'|' -f1)
    url=$(echo $service | cut -d'|' -f2)
    port=$(echo $service | cut -d'|' -f3)

    # Matar cualquier proceso anterior que esté ejecutando el JAR o usando el puerto
    kill_by_jar "$jar"
    kill_by_port "$port"

    echo "Iniciando $jar..."

    # Ejecutar el microservicio en segundo plano y redirigir la salida a un archivo de log
    java -jar "$jar" > "$jar.log" 2>&1 &

    # Esperar hasta que el microservicio esté listo
    wait_for_service "$url"

    if [[ $? -ne 0 ]]; then
        echo "Fallo al iniciar $jar. Abortando."
        cleanup
    fi

    # Esperar un poco para asegurar que el servicio está completamente estable antes de continuar
    sleep 5
done

echo "Todos los microservicios han sido iniciados con éxito."
