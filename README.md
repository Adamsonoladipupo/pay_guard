# PayGuard

PayGuard is a real-time fraud detection backend system built with Spring Boot, PostgreSQL, and JWT Authentication.

The system monitors incoming transaction requests, detects suspicious activities, applies rate limiting, and provides secure admin monitoring APIs for flagged transactions.

---

# Features

## Transaction Ingestion API
- Accepts payment transaction requests
- Validates incoming transaction data
- Stores transaction records securely

## Fraud Detection Engine
- Detects suspicious transaction patterns
- Flags abnormal requests
- Supports extensible fraud detection rules

## Rate Limiting
- Limits excessive requests from the same IP
- Detects potential bot or card-testing attacks

## JWT Authentication
- Secure admin authentication
- Protected admin endpoints
- Token-based authorization

## Admin Dashboard APIs
- View flagged transactions
- View fraud statistics
- Monitor suspicious activities

## Observability & Logging
- Spring AOP logging
- Request monitoring
- Latency tracking
- Error logging

## Exception Handling
- Global exception handling
- Standardized API error responses

---

# Tech Stack

## Backend
- Java 17
- :contentReference[oaicite:0]{index=0}
- Spring Security
- Spring AOP
- Hibernate/JPA

## Database
- :contentReference[oaicite:1]{index=1}

## Authentication
- JWT (JSON Web Tokens)

## Documentation
- Swagger/OpenAPI

---

# System Architecture

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ├── Fraud Detection Engine
   ├── Rate Limiter
   ├── JWT Authentication
   │
   ▼
Repository Layer
   │
   ▼
PostgreSQL Database
```

---

# Core Functionalities

## Fraud Detection Rules
- Excessive requests from same IP
- High-frequency transaction attempts
- Suspicious transaction amounts
- Invalid card patterns
- Blacklisted merchants

---

# API Endpoints

## Transactions

### Create Transaction
```http
POST /api/v1/transactions
```

---

## Authentication

### Admin Login
```http
POST /api/v1/admin/login
```

---

## Admin Dashboard

### Get Flagged Transactions
```http
GET /api/v1/admin/flagged-transactions
```

---

# Sample Transaction Request

```json
{
  "cardNumber": "5399838383838381",
  "amount": 1200,
  "merchantId": "M123",
  "ipAddress": "192.168.1.1"
}
```

---

# Project Structure

```text
src/main/java/com/payguard
│
├── controller
├── service
├── repository
├── model
├── dto
├── security
├── exception
├── config
├── aspect
├── limiter
└── util
```

---

# Security Features

- JWT Authentication
- Password Hashing
- Input Validation
- Exception Handling
- Rate Limiting
- HTTPS-ready

---

# Future Improvements

- Redis distributed rate limiter
- Kafka event streaming
- Machine learning fraud scoring
- Real-time notification system
- Geo-location anomaly detection
- AI-powered fraud analysis

---

# Non-Functional Requirements

- Low latency responses
- High scalability
- Secure authentication
- Fault tolerance
- Observability support

---

# Getting Started

## Clone Repository

```bash
git clone https://github.com/yourusername/payguard.git
```

---

## Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/payguard_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

---

## Run Application

```bash
./mvnw spring-boot:run
```

---

# UML Diagrams

The project includes:
- Use Case Diagram
- Class Diagram
- Sequence Diagram
- Activity Diagram
- Component Diagram
- Deployment Diagram

---

# Roadmap

## Phase 1
- Core transaction ingestion API
- Database setup
- Basic fraud rules

## Phase 2
- JWT authentication
- Admin dashboard APIs
- Rate limiter

## Phase 3
- AOP logging
- Monitoring and observability
- Performance optimization

## Phase 4
- Redis integration
- Kafka streaming
- ML fraud detection

---

# License

MIT License
