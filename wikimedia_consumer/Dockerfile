FROM maven AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY . .
RUN mvn package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app/target
COPY --from=builder /app/target/wikimedia_consumer.jar .
EXPOSE 80
ENTRYPOINT ["java", "-jar", "wikimedia_consumer.jar"]