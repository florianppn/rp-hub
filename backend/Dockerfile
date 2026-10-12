FROM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /build

COPY mvnw pom.xml ./
COPY .mvn .mvn/

RUN ./mvnw dependency:go-offline -B

COPY src src/
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]