# Task 3 — SRS / Architecture

## Functional requirements
Create, view, update and search requests; assign engineers; status workflow; dashboard; health endpoint; automated tests.

## Non-functional
Responsive UI, maintainable code, Maven build, automated testing, container portability and repeatable deployment.

## Architecture
Browser → Spring Boot MVC → Service → JPA Repository → H2/MySQL.
CI/CD: GitHub → Jenkins → Maven → Selenium → Docker → Nginx/Application.

## Data model
ServiceRequest(id, customerName, location, issue, priority, engineer, status, createdAt).

## API list
GET /health
GET /api/requests
GET /requests
GET /requests?q=...
GET /requests/new
POST /requests
GET /requests/{id}/edit
POST /requests/{id}

## Technology
Java 25 LTS, Spring Boot 4.1.0, Maven, Thymeleaf, JPA, H2 local profile, MySQL profile, Selenium, Jenkins, Docker, Nginx, Ansible.
