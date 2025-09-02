# Etapa de construcción
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copiar archivos de configuración y código fuente
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar el archivo JAR generado
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
