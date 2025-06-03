# Imagen base con JDK 21
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR compilado al contenedor
COPY target/user-microservices-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
