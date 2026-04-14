<div align="center">

# 📓 JournalApplication — Secure Personal Journaling REST API

**A full-featured backend journaling platform with role-based access, MongoDB transactions, and email notifications**

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.12-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)](https://www.mongodb.com/)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

---

*A production-ready REST API that allows users to create, manage, and organize personal journal entries with robust authentication, role-based authorization, transactional data integrity, and integrated email capabilities.*

</div>

---

## 🎯 Problem Statement

Personal journaling helps with self-reflection, mental clarity, and productivity tracking — but most solutions are either:

- ❌ Insecure, with no proper authentication or authorization
- ❌ Lacking multi-user support with isolated, private entries
- ❌ Missing role-based access for administrative oversight
- ❌ Not built for extensibility (email notifications, data exports, etc.)

**JournalApplication** solves this by providing a **secure, multi-user REST API** where each user has their own private journal space, protected by Spring Security with BCrypt encryption, role-based access control (USER / ADMIN), MongoDB transactional integrity, and built-in email notification services.

---

## ✨ Key Features

| Feature | Description |
|---|---|
| 🔐 **HTTP Basic Authentication** | Secure authentication using Spring Security with BCrypt password hashing |
| 👥 **Multi-User Support** | Each user has their own isolated journal space with private entries |
| 📝 **Full CRUD for Journals** | Create, read, update, and delete journal entries linked to authenticated users |
| 🛡️ **Role-Based Authorization** | `USER` and `ADMIN` roles with endpoint-level access control |
| 🏛️ **Admin Dashboard** | Admins can view all users and create new admin accounts |
| 📧 **Email Notifications** | Integrated SMTP email service using Spring Mail (Gmail SMTP) |
| 🔗 **DBRef Relationships** | Journal entries linked to users via MongoDB `@DBRef` references |
| 💾 **MongoDB Transactions** | Transactional operations ensuring data integrity across collections |
| 🔍 **Custom Queries** | Advanced MongoDB queries using `MongoTemplate` with regex and criteria-based filtering |
| 🛡️ **Public Registration** | Open user registration endpoint with secured authenticated routes |

---

## 🏗️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| **Java** | 21 | Core programming language |
| **Spring Boot** | 3.4.12 | Application framework |
| **Spring Security** | 6.x | Authentication & role-based authorization |
| **Spring Data MongoDB** | — | MongoDB ODM & repository abstraction |
| **MongoDB** | 6+ | NoSQL document database |
| **Spring Mail** | — | SMTP email service integration |
| **Lombok** | 1.18.42 | Boilerplate code reduction |
| **BCrypt** | — | Password hashing & encoding |
| **Maven** | 3.9+ | Build & dependency management |

---

## 📐 System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     CLIENT (Postman / Frontend)                  │
│                                                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌───────────────────────┐ │
│  │  Public APIs  │  │ Journal APIs │  │     Admin APIs        │ │
│  │  (No Auth)    │  │ (USER Auth)  │  │  (ADMIN Role Only)    │ │
│  └──────────────┘  └──────────────┘  └───────────────────────┘ │
└────────────────────────────┬────────────────────────────────────┘
                             │  HTTP Basic Auth
                             ▼
┌─────────────────────────────────────────────────────────���───────┐
│                  SERVER (Spring Boot 3.4.12)                    │
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                    Security Layer                          │  │
│  │  ┌──────────────────┐  ┌────────────────────────────────┐ │  │
│  │  │ SecurityFilterChain │  │ CustomUserDetailsService     │ │  │
│  │  │ • /api/public/** │  │ • Loads user from MongoDB      │ │  │
│  │  │   → permitAll()  │  │ • Maps roles to authorities    │ │  │
│  │  │ • /api/journal/**│  └────────────────────────────────┘ │  │
│  │  │ • /api/user/**   │  ┌────────────────────────────────┐ │  │
│  │  │   → authenticated│  │ BCryptPasswordEncoder          │ │  │
│  │  │ • /api/admin/**  │  │ • Hashes passwords on signup   │ │  │
│  │  │   → hasRole(ADMIN│  │ • Verifies on authentication   │ │  │
│  │  └──────────────────┘  └────────────────────────────────┘ │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌─────────────────┐  ┌──────────────────────────────────────┐  │
│  │   Controllers    │  │            Services                  │  │
│  │                  │  │                                      │  │
│  │ • PublicCtrl     │→│  • UserService (CRUD + BCrypt)       │  │
│  │ • JournalCtrl    │→│  • JournalEntryService (Transact.)  │  │
│  │ • UserCtrl       │→│  • CustomUserDetailsService          │  │
│  │ • AdminCtrl      │→│  • EmailService (SMTP)               │  │
│  └─────────────────┘  └──────────────┬───────────────────────┘  │
│                                       │                         │
│  ┌────────────────────────────────────▼──────────────────────┐  │
│  │                    Repositories                            │  │
│  │  • JournalEntryRepo (MongoRepository)                     │  │
│  │  • UserRepo (MongoRepository + custom query methods)      │  │
│  │  • UserRepository (MongoTemplate + Criteria queries)      │  │
│  └──────────────────────────┬────────────────────────────────┘  │
└─────────────────────────────┼───────────────────────────────────┘
                              │
                              ▼
                   ┌─────────────────────┐
                   │      MongoDB        │
                   │  ┌───────────────┐  │
                   │  │  users        │  │
                   │  │  journalEntry │  │
                   │  └───────────────┘  │
                   └─────────────────────┘
```

---

## 🗄️ Data Model

```
┌──────────────────────────────┐          ┌──────────────────────────────┐
│           User               │          │       JournalEntry           │
├──────────────────────────────┤          ├──────────────────────────────┤
│ _id        : ObjectId (PK)  │          │ _id      : ObjectId (PK)    │
│ userName   : String (unique) │          │ name     : String (required)│
│ password   : String (BCrypt) │          │ content  : String           │
│ email      : String          │          │ date     : LocalDateTime    │
│ summary    : boolean         │          └──────────────────────────────┘
│ roles      : List<String>    │                     ▲
│ journalList: List<@DBRef>  ──┼─────────────────────┘
│              (JournalEntry)  │         @DBRef relationship
└──────────────────────────────┘         (One User → Many Journals)
```

### Relationship Details

| Aspect | Description |
|---|---|
| **User → JournalEntry** | One-to-Many via `@DBRef` — each user owns a list of journal entries |
| **Password Storage** | BCrypt-encoded, never stored in plain text |
| **Unique Constraint** | `userName` is indexed as unique (`@Indexed(unique = true)`) |
| **Roles** | Stored as `List<String>` — supports `["USER"]` and `["USER", "ADMIN"]` |
| **Transactions** | `MongoTransactionManager` enabled for atomic multi-collection operations |

---

## 🔌 API Endpoints

### 🌐 Public (`/api/public`) — No Authentication Required
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/public/new-user` | Register a new user account |
| `GET` | `/api/public/send-mail` | Trigger a test email notification |

### 📝 Journal Entries (`/api/journal`) — 🔒 Authenticated (USER)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/journal/get-all` | Get all journal entries (global) |
| `GET` | `/api/journal/get-by-user` | Get authenticated user's journal entries |
| `POST` | `/api/journal/new-entry` | Create a new journal entry for current user |
| `PUT` | `/api/journal/update-journal/{id}` | Update a journal entry by ID |
| `DELETE` | `/api/journal/delete-by-id/{id}` | Delete a journal entry by ID |

### 👤 User Management (`/api/user`) — 🔒 Authenticated (USER)
| Method | Endpoint | Description |
|---|---|---|
| `PUT` | `/api/user/update-user` | Update authenticated user's profile |
| `DELETE` | `/api/user/delete-user` | Delete authenticated user's account |

### 🛡️ Admin (`/api/admin`) — 🔒 Requires ADMIN Role
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/admin/get-all-users` | Get all registered users |
| `POST` | `/api/admin/save-admin` | Create a new admin account |

---

## 🔐 Authentication & Authorization Flow

```
┌──────────┐                    ┌──────────────────┐           ┌───────────┐
│  Client   │                    │   Spring Security │           │  MongoDB  │
└────┬─────┘                    └────────┬─────────┘           └─────┬─────┘
     │                                   │                           │
     │  1. POST /api/public/new-user     │                           │
     │  { userName, password, email }    │                           │
     │──────────────────────────────────►│  BCrypt encode password   │
     │                                   │──────────────────────────►│
     │   201 CREATED                     │   Save user (role: USER)  │
     │◄──────────────────────────────────│◄──────────────────────────│
     │                                   │                           │
     │  2. GET /api/journal/get-by-user  │                           │
     │  Authorization: Basic base64(...) │                           │
     │──────────────────────────────────►│                           │
     │                                   │  loadUserByUsername()     │
     │                                   │──────────────────────────►│
     │                                   │◄──────────────────────────│
     │                                   │  BCrypt.matches()         │
     │                                   │  Check: role == USER ✅   │
     │   200 OK [journal entries]        │                           │
     │◄──────────────────────────────────│                           │
     │                                   │                           │
     │  3. GET /api/admin/get-all-users  │                           │
     │  Authorization: Basic base64(...) │                           │
     │──────────────────────────────────►│                           │
     │                                   │  Check: role == ADMIN?    │
     │   403 FORBIDDEN (if USER only)    │                           │
     │◄──────────────────────────────────│                           │
     │   200 OK (if ADMIN) ✅            │                           │
     │◄──────────────────────────────────│                           │
```

### Security Rules Summary

| Endpoint Pattern | Access Level | Auth Type |
|---|---|---|
| `/api/public/**` | Everyone | None |
| `/api/journal/**` | Authenticated users | HTTP Basic |
| `/api/user/**` | Authenticated users | HTTP Basic |
| `/api/admin/**` | ADMIN role only | HTTP Basic |

---

## 📁 Project Structure

```
JournalApplication/
├── journalApplication/                                    # Spring Boot Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/pranay/journalApplication/
│   │   │   │   ├── JournalApplication.java                # Main entry + Transaction config
│   │   │   │   │
│   │   │   │   ├── Configuration/
│   │   │   │   │   └── SecurityConfiguration.java         # Spring Security config
│   │   │   │   │       • SecurityFilterChain (URL-based rules)
│   │   │   │   │       • AuthenticationManager (BCrypt)
│   │   │   │   │       • CSRF disabled for REST API
│   │   │   │   │
│   │   │   │   ├── Controller/
│   │   │   │   │   ├── PublicController.java              # Open endpoints (register, email)
│   │   │   │   │   ├── JournalEntryController.java        # CRUD for journal entries
│   │   │   │   │   ├── UserController.java                # User profile management
│   │   │   │   │   └── AdminController.java               # Admin-only operations
│   │   │   │   │
│   │   │   │   ├── Entity/
│   │   │   │   │   ├── JournalEntry.java                  # Journal document (name, content, date)
│   │   │   │   │   └── User.java                          # User document (credentials, roles, @DBRef)
│   │   │   │   │
│   │   │   │   ├── Repository/
│   │   │   │   │   ├── JournalEntryRepo.java              # MongoRepository for journals
│   │   │   │   │   ├── UserRepo.java                      # MongoRepository for users
│   │   │   │   │   └── UserRepository.java                # Custom MongoTemplate queries
│   │   │   │   │       • Regex email validation query
│   │   │   │   │       • Criteria-based user filtering
│   │   │   │   │
│   │   │   │   └── Service/
│   │   │   │       ├── JournalEntryService.java           # Journal CRUD + user linkage
│   │   │   │       ├── UserService.java                   # User CRUD + BCrypt + role assignment
│   │   │   │       ├── CustomUserDetailsService.java      # Spring Security UserDetailsService
│   │   │   │       └── EmailService.java                  # SMTP email sender (JavaMailSender)
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.yml                        # MongoDB, Mail, Server config
│   │   │       └── application.properties
│   │   │
│   │   └── test/java/                                     # Test directory
│   │
│   └── pom.xml                                            # Maven dependencies
│
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** (JDK)
- **Maven 3.9+**
- **MongoDB 6+** (running locally or via Atlas)

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/pranaypanakanti/JournalApplication.git
cd JournalApplication/journalApplication
```

### 2️⃣ Configure MongoDB & Email

Edit `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: journalApplication
  data:
    mongodb:
      host: localhost
      port: 27017
      database: journaldb
      auto-index-creation: true
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-app-password         # Use Gmail App Password, not your real password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

> ⚠️ **Important:** For Gmail SMTP, generate an [App Password](https://support.google.com/accounts/answer/185833) — do not use your Google account password.

### 3️⃣ Start MongoDB

```bash
# If installed locally
mongod

# Or using Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### 4️⃣ Run the Application

```bash
./mvnw spring-boot:run
```

The API server will start at `http://localhost:8080`

### 5️⃣ Test the API

**Register a new user:**
```bash
curl -X POST http://localhost:8080/api/public/new-user \
  -H "Content-Type: application/json" \
  -d '{"userName": "john", "password": "secret123", "email": "john@example.com"}'
```

**Create a journal entry (authenticated):**
```bash
curl -X POST http://localhost:8080/api/journal/new-entry \
  -u john:secret123 \
  -H "Content-Type: application/json" \
  -d '{"name": "My First Entry", "content": "Today was a productive day!"}'
```

**Get your journal entries:**
```bash
curl -X GET http://localhost:8080/api/journal/get-by-user \
  -u john:secret123
```

## 👨‍💻 Author

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/pranaypanakanti">
        <img src="https://avatars.githubusercontent.com/u/211188683?v=4" width="100px;" alt=""/>
        <br />
        <sub><b>Pranay Panakanti</b></sub>
      </a>
      <br />
      <sub>Full Stack Developer</sub>
    </td>
  </tr>
</table>

---

<div align="center">

**⭐ If you found this project helpful, give it a star!**

*Built with ❤️ using Spring Boot & MongoDB*

[🐛 Report Bug](https://github.com/pranaypanakanti/JournalApplication/issues) · [💡 Request Feature](https://github.com/pranaypanakanti/JournalApplication/issues)

</div>
