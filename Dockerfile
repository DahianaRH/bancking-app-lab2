FROM eclipse-temurin:17-jre AS build

WORKDIR /app

COPY target/banck-lab2.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]