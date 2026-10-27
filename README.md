# ATTEND - Backend Service

Spring Boot RESTful API service for the ATTEND Student Attendance Tracker.

## Technology Stack
- Java 21+
- Spring Boot 3.3.5
- Spring Data JPA / Hibernate
- MySQL 8.0
- Maven 3.9+

## Configuration
Edit `src/main/resources/application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/attend_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

## Build and Run
```bash
# Build
mvn clean package -DskipTests

# Run
java -jar target/attend-backend-1.0.0.jar
```
Runs at `http://localhost:8080`.
