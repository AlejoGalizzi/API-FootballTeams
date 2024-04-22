FROM eclipse-temurin:17-jdk-alpine AS builder
RUN apk add --no-cache maven
WORKDIR /api-teams
COPY pom.xml .
COPY src ./src
RUN mvn clean package install
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /api-teams
COPY --from=builder /api-teams/target/teams-0.0.1-SNAPSHOT.jar /api-teams/api-teams.jar
EXPOSE 8080
CMD ["java", "-jar", "api-teams.jar"]