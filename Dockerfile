# Stage 1 - Build jar
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src
RUN ./mvnw package -DskipTests

# Stage 2 - Run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/moneymanager-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]