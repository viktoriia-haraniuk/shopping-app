# shopping-app
This is web application which simulates online store.
Endpoints and their functions:
<pre>
[POST] - /register -  register new user with shopping cart;
[GET] -  /orders - get all pending/successful user's orders
[POST] - /orders/complete - create new order
[PUT] - /orders/complete/pay/{id} - set order as paid
[POST] - /products - create new product
[GET] -/products - show list of all available products
[PUT] - /shopping-cart - add product to shopping cart
</pre>

All endpoints send and receive JSON data, except login page. It is generated by SPRING security. 
Almost all the endpoints are secured by role based authorization. 
For more details check SecurityConfig class.

# Project architecture 
Repository - Data access layer
Service - Application layer
Model - Presentation layer

# Technologies used in project
- Java 11
- Apache Maven
- H2
- Spring BOOT
- Spring Security
- Hibernate
- Mockito

# How to run a project?
1. Clone this project
2. Run main method in ShoppingAppApplication.class
3. After build, you can use injected admin user to test admin privileges. Login details: username:admin123, password:admin123
4. To register new user send POST request to http://localhost:8080/register with username and password
