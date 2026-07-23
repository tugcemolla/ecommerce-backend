#E-Commerce Backend API

A RESTful e-commerce backend application developed with Spring Boot. This project provides secure user authentication, product and category management, shopping cart functionality, order processing, payment operations, and Docker support.

##Features

- JWT Authentication & Authorization
- User Registration & Login
- Product CRUD Operations
- Category Management
- Shopping Cart
- Order Management
- Payment Module
- Spring Security
- Swagger/OpenAPI Documentation
- Docker & Docker Compose Support
- PostgreSQL Database

##Technologies

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- PostgreSQL
- Maven
- Docker
- Docker Compose
- Swagger (OpenAPI)

##Project Structure

```
src
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
├── service
└── EcommerceApplication
```

##Getting Started

### Clone the repository

```bash
git clone https://github.com/<your-username>/ecommerce-backend.git
```

### Run with Docker

```bash
docker compose up --build
```

The application will start on:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

##Main API Endpoints

### Authentication
- POST `/api/auth/register`
- POST `/api/auth/login`

### Products
- GET `/api/products`
- POST `/api/products`
- PUT `/api/products/{id}`
- DELETE `/api/products/{id}`

### Categories
- GET `/api/categories`
- POST `/api/categories`

### Cart
- POST `/api/cart/add`
- GET `/api/cart`
- PUT `/api/cart/update`
- DELETE `/api/cart/remove`

### Orders
- POST `/api/orders`
- GET `/api/orders`

### Payments
- POST `/api/payments`

---

This project was developed for educational purposes.
