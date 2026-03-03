FROM eclipse-temurin:22-jdk-alpine AS build
WORKDIR /app
COPY . .

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:22-jre-alpine
WORKDIR /app

COPY --from=build /app/target/cartas-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
