# VirtualBookStore

A Spring Boot based backend for a Virtual Book Store application featuring JWT security, book and cart management, and user reviews.

## Features

- User registration and authentication using JWT.
- When a user is created, it is **by default created as a "USER"** role.
- First, create one admin user manually. Afterwards, admins can promote other users to "ADMIN" role using the admin controller.
- CRUD operations for books (create, read, update, delete).
- Add books to shopping cart and place orders.
- When you add books to the cart, you can place the order.
- After placing an order, your cart is automatically emptied.
- Payment is processed using the order number.
- **Payment integration:** Dummy payment available at http://localhost:8888/payment
- Submit and fetch book reviews with prevention of duplicate reviews by the same user.
- Role-based authorization (Admin and User roles).
- RESTful APIs documented with OpenAPI/Swagger at http://localhost:8888/swagger-ui.html
- Admin authorization tests might require running requests via Postman if UI not working.
- Unit and integration tests implemented with Mockito and MockMvc.

## Technologies

- Java 21
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA with MySQL
- Mockito & JUnit 5 for testing
- Swagger/OpenAPI documentation

## Prerequisites

- Java 21+
- MySQL Database
- Maven 3.8+

## Setup

1. Clone the repository:  
   https://github.com/Rohitarote2000/virtualBookStorePlatform.git


                          
