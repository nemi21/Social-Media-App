Social Media App

A Java Spring Boot project for a social media application with user management, posts, likes, reactions, and followers/following functionality.

Features
User Management (CRUD)
  -Create, read, update, delete users
Follow / Unfollow Users
  -Users can follow and unfollow each other
  -View followers and following lists
  -Handles edge cases like self-follow or duplicate follows
Posts
  -Users can create and delete posts
  -Retrieve posts with associated likes, reactions, and comments
Likes
  -Users can like posts and comments
  -Count total likes
Reactions
  -Users can react to posts and comments with different reaction types
  -Count reactions per type
REST APIs with DTOs for clean responses
Custom Exceptions & Error Handling
  -Provides clear JSON responses for invalid operations

MySQL Integration
  Database-driven application with tables: users, posts, comments, follows, likes, reactions

API Endpoints
Users
| Method | Endpoint      | Description       |
| ------ | ------------- | ----------------- |
| POST   | `/users`      | Create a new user |
| GET    | `/users`      | Get all users     |
| GET    | `/users/{id}` | Get user by ID    |
| PUT    | `/users/{id}` | Update user       |
| DELETE | `/users/{id}` | Delete user       |

Follow System
| Method | Endpoint                                     | Description                      |
| ------ | -------------------------------------------- | -------------------------------- |
| POST   | `/users/{followerId}/follow/{followingId}`   | Follow a user                    |
| DELETE | `/users/{followerId}/unfollow/{followingId}` | Unfollow a user                  |
| GET    | `/users/{userId}/followers`                  | Get followers of a user          |
| GET    | `/users/{userId}/following`                  | Get users this user is following |

Posts
| Method | Endpoint      | Description                                                 |
| ------ | ------------- | ----------------------------------------------------------- |
| POST   | `/posts`      | Create a new post                                           |
| GET    | `/posts`      | Get all posts with likes, reactions, comments               |
| GET    | `/posts/{id}` | Get a specific post with likes, reactions, comments         |
| DELETE | `/posts/{id}` | Delete a post and all associated likes, reactions, comments |

Likes
| Method | Endpoint                                   | Description                       |
| ------ | ------------------------------------------ | --------------------------------- |
| POST   | `/likes/post/{postId}/user/{userId}`       | Like a post                       |
| POST   | `/likes/comment/{commentId}/user/{userId}` | Like a comment                    |
| GET    | `/likes/post/{postId}/count`               | Get number of likes for a post    |
| GET    | `/likes/comment/{commentId}/count`         | Get number of likes for a comment |

Reactions
| Method | Endpoint                                                           | Description                     |
| ------ | ------------------------------------------------------------------ | ------------------------------- |
| POST   | `/reactions/post/{postId}/user/{userId}?type={ReactionType}`       | React to a post                 |
| POST   | `/reactions/comment/{commentId}/user/{userId}?type={ReactionType}` | React to a comment              |
| GET    | `/reactions/post/{postId}`                                         | Get all reactions for a post    |
| GET    | `/reactions/comment/{commentId}`                                   | Get all reactions for a comment |
| GET    | `/reactions/post/{postId}/count`                                   | Count reactions for a post      |
| GET    | `/reactions/comment/{commentId}/count`                             | Count reactions for a comment   |

Getting Started
Prerequisites

Java 17+
Maven
MySQL running locally or in Docker

Setup Steps
1.Clone the repository
  bash:
  git clone <your-repo-url>
  cd <repo-folder>

2.Configure database connection
  Update application.properties with your MySQL credentials:
  spring.datasource.url=jdbc:mysql://localhost:3306/socialapp
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  spring.jpa.hibernate.ddl-auto=update


3.Run the application
  bash:
  ./mvnw spring-boot:run

4.Seed test users (optional)
  Use the /users POST endpoint or SQL to create sample users for testing the follow system.
5.Test APIs
  Use Postman or Swagger UI (http://localhost:8080/swagger-ui.html)




