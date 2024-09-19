#Utilizar la imagen oficial de OpenJDK 21
FROM openjdk:21-jdk-slim

#Establecer el directorio de trabajo
WORKDIR /app

RUN mvn clean install

#Copiar el archivo JAR de la aplicación al contenedor
COPY target/SCRUM-APP-0.0.1-SNAPSHOT.jar app.jar

#Exponer el puerto en el que la aplicación se ejecuta
EXPOSE 8080

#Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

