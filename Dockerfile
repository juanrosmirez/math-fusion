# Usamos una imagen base de OpenJDK para Java 11
FROM openjdk:21-jdk

# Etiqueta para identificar el autor del Dockerfile
LABEL author="Juan Ignacio Rosmirez"

# Establecemos el directorio de trabajo en /app
WORKDIR /app

# Copiamos el archivo JAR de tu proyecto Spring Boot desde build/libs/
# en tu proyecto Gradle al contenedor Docker en /app/app.jar
COPY build/libs/*.jar /app/app.jar

# Puerto en el que se ejecutará la aplicación Spring Boot (ajusta según tus necesidades)
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "app.jar"]