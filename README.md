# JWT Authentication Microservice

A Spring Boot REST API that implements JWT (JSON Web Token) based authentication and authorization.

## Features

- User Login
- JWT Token Generation
- Store JWT in MySQL Database
- Role-Based Access (ADMIN, USER)
- Scope-Based Authorization
- Token Expiration
- Token Validation
- Interceptor Based Authentication (Upcoming)
- Logout (Upcoming)

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- JWT (JJWT)
- Maven
- Git & GitHub

## Project Structure

```
src
├── controller
├── dto
├── entity
├── enums
├── repository
├── service
└── util
```

## API

### Login

**POST** `/auth/login`

Request

```json
{
    "username": "admin",
    "password": "admin123"
}
```

Response

```json
{
    "token":"eyJhbGc..."
}
```

## Database Tables

- users
- user_tokens

## Upcoming Features

- JWT Validation
- JWT Interceptor
- Role & Scope Validation
- Logout API
- Refresh Token

## Author

**Rohit Dadgelwar**

GitHub: https://github.com/rohitdadgelwar45
