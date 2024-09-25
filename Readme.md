# SCRUM.APP Management System

## Overview
The SCRUM.APP Management System is a Spring Boot-based platform designed to manage projects, tasks, users, and authentication within a SCRUM framework. The system provides a secure backend for handling the different roles, projects, and tasks that a team might encounter in an agile environment.

## Features

- **User Management:** Register, login, and manage user accounts with role-based access control.
- **Project Management:** Add, update, and track the progress of projects.
- **Task Management:** Create, update, and monitor tasks within projects.
- **Authentication:** Secure API endpoints using JWT (JSON Web Tokens).
- **Role-based Access Control:** Differentiate access between admin, manager, and regular user roles.

## Technology Stack

- **Java**
- **Spring Boot**
- **Spring Security**
- **JPA / Hibernate**
- **BCrypt Password Encoder**
- **JSON Web Tokens (JWT)**
- **Mockito (for unit testing)**
- **Docker**

## Project Structure

The project follows a standard Spring Boot application structure:

- **config:** Configuration classes for Spring Security, application settings, and object mapping.
- **controllers:** REST API endpoints for users, projects, tasks, and authentication.
- **dtos:** Data Transfer Objects (DTOs) for handling requests and responses.
- **jwt:** JWT authentication filter and utilities.
- **models:** Entity classes representing database tables.
- **repositories:** JPA repositories for database operations.
- **services:** Business logic implementation for handling user, project, task, and authentication functionalities.
- **test:** Unit tests for controllers and services.

## Docker Setup

### Docker Compose Configuration

This project includes a `docker-compose.yml` file for setting up the application using Docker:

```yaml
version: '3.8'

services:
  springboot-app:
    build: .
    container_name: scrum-app
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://host.docker.internal:3306/db_scrumapp
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
```

### Dockerfile

The Dockerfile is used to build a Docker image for the SCRUM.APP application:

```dockerfile
# Use the official OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Build the application using Maven
RUN mvn clean install

# Copy the JAR file from the target directory to the container
COPY target/SCRUM-APP-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Running the Application with Docker

1. **Build the Docker image:**
   ```bash
   docker-compose build
   ```

2. **Run the application:**
   ```bash
   docker-compose up
   ```

3. **Access the application:**
   Open your browser and navigate to `http://localhost:8080`.

## Key Components

### Configuration
- **ApplicationConfig:** Configures beans for authentication, password encoding, and user details service.
- **WebSecurityConfig:** Sets up security filters and access rules for different endpoints.
- **JacksonConfig:** Customizes the ObjectMapper for JSON serialization/deserialization.

### Controllers
- **AuthController:** Manages user authentication and registration.
- **ProjectController:** Handles project-related operations such as creating, updating, and deleting projects.
- **TaskController:** Manages tasks within projects, including their creation, updating, and status tracking.
- **UserController:** Handles user management operations such as creating, updating, and deleting users.

### Models
- **User:** Represents users and implements `UserDetails` for Spring Security.
- **Project:** Represents project entities with attributes such as name, description, and completion status.
- **Task:** Represents tasks within a project, with attributes such as name, description, and completion status.

### API Endpoints

- **Authentication:** `/api/auth/login`, `/api/auth/register`
- **Projects:** `/api/v1/projects`
- **Tasks:** `/api/v1/tasks`
- **Users:** `/api/v1/users`

## Unit Testing

The project includes comprehensive unit tests for controllers and services using Mockito:

### Controller Tests
- Test cases cover all API endpoints.
- Mock service layers to isolate controller logic.
- Verify correct HTTP status codes and response bodies.

### Service Tests
- Test business logic in isolation.
- Mock repository layers to focus on service functionality.
- Cover various scenarios, including success cases and error handling.

### Test Coverage
- **AuthController and AuthService**
- **ProjectController and ProjectService**
- **TaskController and TaskService**
- **UserController and UserService**

To run the tests:
```bash
./mvnw test
```

## Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/scrum-app.git
   ```
2. **Configure your database settings** in `application.properties`.
3. **Run the application** using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

## Security

This application uses Spring Security with JWT for authentication. Include the JWT token in the `Authorization` header for accessing protected endpoints.
