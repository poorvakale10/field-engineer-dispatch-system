# DispatchFlow — Field Engineer Dispatch System - Field Operations MVP

A polished academic DevOps + Selenium MVP for field engineer dispatch operations.

## MVP
Create, view, update and search requests; engineer assignment; role-oriented status workflow; dashboard; Selenium tests; Maven; Jenkins; Docker; Nginx; Ansible.

## Stack
Java 25 LTS, Spring Boot 4.1.0, Maven, Thymeleaf, H2/MySQL, Selenium 4.50.0, Git/GitHub, Jenkins, Docker, Nginx and Ansible.

## Run locally
```bash
mvn clean package
mvn spring-boot:run
```
Open http://localhost:8080 or http://localhost:8080/login

## Selenium
Start the app, then run:
```bash
mvn test
```

## Docker
```bash
mvn clean package -DskipTests
docker build -t poorvakale/dispatchflow:1.0.0 .
docker run -d --name dispatchflow -p 8080:8080 poorvakale/dispatchflow:1.0.0
```

## Git branches
main = release, develop = integration, feature/<name> = feature work, bugfix/<name> = fixes, release/vX.Y.Z = release.

## Definition of Done
Acceptance criteria met, code reviewed, tests pass, docs updated, CI green and deployable artifact generated.

## Student
Poorva Kale — 23102C0015
