# Usa la imagen oficial de OpenJDK con JDK 17
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR del microservicio (ajustaremos el nombre exacto más adelante)
COPY target/MicroNotification-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre tu microservicio
EXPOSE 8085

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
