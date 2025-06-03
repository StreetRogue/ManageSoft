# Imagen base con JDK 21
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR compilado al contenedor
COPY target/MicroEmpresa-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del API Gateway (según tu application.yml)
EXPOSE 8082

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
