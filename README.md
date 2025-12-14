# JournalApplication

A Spring Boot backend for a journal application with full CRUD for users and journals, MongoDB persistence, secure authentication/authorization (JWT + role-based), password encryption, automated email sending (verification / notifications), and other production-ready features.

---

## Table of contents
- About
- Key features
- Tech stack
- Architecture overview
- Getting started (prerequisites & quick start)
- Configuration (env / application properties)
- Authentication & security
- Email (automated mail sender)
- Data model (brief)
- Tests
- Contact

---

## About
JournalApplication is a backend service built with Spring Boot that provides a secure REST API for managing users and their journals. It uses MongoDB for data storage and implements secure authentication using encrypted credentials and JWT tokens. Role-based access control separates user and admin capabilities. The app can send automated emails (for account verification, password reset, and notifications).

---

## Key features
- CRUD operations for:
  - Users (register, update profile, delete, role management)
  - Journals (create, read, update, delete, list, search/pagination)
- Authentication & Authorization:
  - JWT-based stateless authentication
  - Role-based access (ROLE_USER, ROLE_ADMIN)
- Security:
  - Password hashing (BCrypt recommended)
  - Token expiration and validation
- MongoDB integration (document store)
- Automated email sending (SMTP) for account verification and notifications
- Clean REST API suitable for consumption by web or mobile clients

---

## Tech stack
- Java (Spring Boot)
- Spring Security (JWT)
- Spring Data MongoDB
- Spring Mail (JavaMailSender) or equivalent
- Build: Maven
- Database: MongoDB

---

## Architecture overview
- Controllers: REST endpoints for authentication, users, journals, and admin operations.
- Services: Business logic for authentication, user and journal management, email sending.
- Repositories: Spring Data MongoDB repositories for persistence.
- Security: JWT filter, token provider, password encoder, and role-based access checks.

---

## Getting started

### Prerequisites
- Java 17+ (or the version your project targets)
- Maven
- MongoDB (local or hosted)
- SMTP account credentials for automated emails (Gmail, SendGrid, Mailgun, etc.)
- Git

### Quick start (local)
1. Clone the repo:
   git clone https://github.com/pranaypanakanti/JournalApplication.git
   cd JournalApplication

2. Configure environment variables or application properties (see next section).

3. Build:
   mvn clean package

4. Run:
   mvn spring-boot:run
   or
   java -jar target/<artifact-name>.jar

The server starts on the configured port (default commonly 8080).

---

## Configuration

Provide configuration via environment variables or application.properties / application.yml.

Common variables (example names — adapt to your property keys):

- spring.data.mongodb.uri or MONGODB_URI  
  Example: mongodb://localhost:27017/journaldb

- JWT_SECRET  
  Example: a-very-secret-key-change-me

- JWT_EXPIRATION_MS  
  Example: 86400000  (24h in milliseconds)

- spring.mail.host (MAIL_HOST)  
- spring.mail.port (MAIL_PORT)  
- spring.mail.username (MAIL_USERNAME)  
- spring.mail.password (MAIL_PASSWORD)  
- app.mail.from (MAIL_FROM)

Example application.properties snippet:
spring.data.mongodb.uri=${MONGODB_URI}
server.port=8080

jwt.secret=${JWT_SECRET}
jwt.expiration-ms=${JWT_EXPIRATION_MS}

spring.mail.host=${MAIL_HOST}
spring.mail.port=${MAIL_PORT}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
app.mail.from=${MAIL_FROM}

Security notes:
- Keep JWT_SECRET and MAIL_PASSWORD out of source control.
- Use a secrets manager (Vault, AWS Secrets Manager, etc.) for production.

---

## Authentication & security details
- Passwords: hashed using a strong algorithm (BCrypt recommended).
- JWT:
  - Signed using JWT_SECRET.
  - Contains user id and roles as claims.
  - Enforces token expiration (JWT_EXPIRATION_MS).
- Role based access control:
  - ROLE_USER: permissions to manage own journals and profile.
  - ROLE_ADMIN: elevated permissions for user management and moderation.
- Always validate JWT signature and expiry for protected endpoints.
- Use HTTPS in production; use secure cookie attributes if storing tokens in cookies.

---

## Email (automated mail sender)
- Uses Spring's JavaMailSender (or a service wrapper).
- Typical flows:
  - Account verification email after registration.
  - Password reset emails.
  - Notification emails (optional).
- Send mail asynchronously (e.g., @Async or message queue) to avoid blocking requests.
- Verify sending domain when using third-party providers (SendGrid, Mailgun, etc.).

---

## Data model (brief)
- User:
  - id (ObjectId)
  - name
  - email (unique)
  - password (hashed)
  - roles (list)
  - enabled / verified
  - createdAt, updatedAt

- Journal:
  - id (ObjectId)
  - title
  - content
  - authorId (reference to User)
  - tags
  - private (boolean)
  - createdAt, updatedAt

Add indexes for frequently queried fields (authorId, createdAt, tags) to improve performance.

---

## Tests
- Run unit tests:
  mvn test

- Integration tests:
  - Use a running MongoDB (or Testcontainers) for realistic integration tests.
  - Mock JavaMailSender when testing email flows.

Recommended tests:
- Auth flows (register, login, token validation)
- Role-based access restrictions
- CRUD operations for users and journals
- Email sending behavior (mocked)

---

## License
If you want to make this project open source, add a LICENSE file (MIT, Apache 2.0, etc.). Currently no license file is included in this README — add one to define repository terms.

---

## Contact
Repository: https://github.com/pranaypanakanti/JournalApplication  
Maintainer: pranaypanakanti (https://github.com/pranaypanakanti)

---
