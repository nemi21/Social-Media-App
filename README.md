Social Media App

A Java Spring Boot project for a social media application.

Features

User management (CRUD)

Create, read, update, delete users

Follow / Unfollow functionality

Users can follow and unfollow each other

View followers and following lists

Handles edge cases like self-follow or duplicate follows

REST APIs

Clean API endpoints with request and response DTOs

Standardized error handling with custom exceptions

MySQL integration

Database-driven application with users and follows tables

API Endpoints
Users
Method	Endpoint	Description
POST	/users	Create a new user
GET	/users	Get all users
GET	/users/{id}	Get user by ID
PUT	/users/{id}	Update user
DELETE	/users/{id}	Delete user
Follow System
Method	Endpoint	Description
POST	/users/{followerId}/follow/{followingId}	Follow a user
DELETE	/users/{followerId}/unfollow/{followingId}	Unfollow a user
GET	/users/{userId}/followers	Get a list of followers
GET	/users/{userId}/following	Get a list of users this user is following
Error Handling

Custom exceptions for:

User not found

Already following

Cannot follow yourself

Follow relationship not found

Returns clear JSON responses instead of generic 500 errors

Technologies

Java 17+

Spring Boot

MySQL

Spring Data JPA

RESTful APIs

Getting Started
Prerequisites

Java 17 or higher

Maven

MySQL running locally or in Docker

Setup Steps

Clone the repository

git clone <your-repo-url>
cd <repo-folder>


Configure database connection

Update application.properties with your MySQL credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/socialapp
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update


Run the application

./mvnw spring-boot:run


Seed test users (optional)

Use the /users POST endpoint or SQL to create sample users for testing the follow system.

Test APIs

Use Postman or Swagger UI (http://localhost:8080/swagger-ui.html)

Test following/unfollowing and retrieving followers/following lists.
