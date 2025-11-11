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


                            #Api Test #

postman collection for virtual book store

New user Registration

http://localhost:8888/auth/signup

{
"name":"user ",
"email": "user@gmail.com",
"password": "user"
}
---------------------------------------------------------
login
http://localhost:8888/auth/login


{

"email": "user@gmail.com",
"password": "user"
}
-------------------------------------------------
Add to cart book admin only

http://localhost:8888/book/addbook

{
"title": "The Great Gatsby",
"author": "F. Scott Fitzgerald",
"price": 499.99,
"stock": 25,
"avgRating": 4.5
}
-----------------------------------------

get All books

http://localhost:8888/book/getAllBooks
-------------------------------------------

find book by id

http://localhost:8888/book/findBookById/2

-----------------------------------------------------------


update book by id Admin only

http://localhost:8888/book/updateBook/52

------------------------------------------------------------

delete bookby id

http://localhost:8888/book/deleteBook/52

-----------------------------------------------------------

Cart

Add to cart

{
"userId": 8,
"bookId": 102,
"quantity": 2
}
-----------------------------------------
get cart by user

http://localhost:8888/api/cart/user/8


----------------------------------------------------

remove book from cart

http://localhost:8888/api/cart/remove/{cart_id}
------------------------------------------------

place order  by user id

http://localhost:8888/api/orders/place/8
-------------------------------------------------


get order by user id
http://localhost:8888/api/orders/user/8

--------------------------------------------------


get order history only admin


http://localhost:8888/api/orders/history
-----------------------------------------------

add review for a book

http://localhost:8888/api/reviews/submit

{
"bookId": 8,
"userId": 8,
"rating": 5,
"comment": "Excellent book! Great insights and character development."
}



----------------------------------------------------

get a review by book id

http://localhost:8888/api/reviews/book/8

--------------------------------------------

update admin

http://localhost:8888/admin/update/role


{
"email": "user@gmail.com",
"role": "ADMIN"
}

--------------------------