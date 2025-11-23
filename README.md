# Social Media App

A secure, full-featured Java Spring Boot REST API for a social media application with user management, posts, comments, likes, reactions, and a follower/following system.

## 🚀 Features

### User Management
- ✅ **Secure Authentication** - BCrypt password hashing
- ✅ **User CRUD Operations** - Create, read, update, delete users
- ✅ **Input Validation** - Email format, username length, password strength
- ✅ **Profile Management** - Bio, avatar URL, and user details

### Social Features
- ✅ **Follow/Unfollow System** - Users can follow and unfollow each other
- ✅ **Paginated Followers/Following** - View followers and following lists with pagination
- ✅ **Duplicate Prevention** - Can't follow yourself or follow the same user twice

### Posts & Engagement
- ✅ **Create Posts** - Text content with optional image URLs
- ✅ **Comments** - Add, update, and delete comments on posts
- ✅ **Likes** - Like posts and comments (duplicate prevention built-in)
- ✅ **Reactions** - React with LIKE, LOVE, HAHA, WOW, SAD, ANGRY
- ✅ **Rich Responses** - Posts include like counts, reaction counts, and comments

### Technical Features
- ✅ **Spring Security** - JWT-ready authentication framework
- ✅ **Database Optimization** - Indexes on frequently queried columns
- ✅ **Performance** - Optimized COUNT queries, connection pooling
- ✅ **Data Integrity** - Unique constraints, foreign keys, cascading deletes
- ✅ **Timestamps** - CreatedAt and UpdatedAt on all entities
- ✅ **Error Handling** - Clear, structured error responses
- ✅ **Clean Architecture** - Controller → Service → Repository pattern

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Security** (BCrypt password encoding)
- **Spring Data JPA** (Hibernate)
- **MySQL 8.0**
- **Maven** (Build tool)
- **Jakarta Validation** (Input validation)

---

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (running locally or in Docker)

---

## ⚙️ Setup & Installation

### 1. Clone the Repository
```bash
git clone https://github.com/nemi21/Social-Media-App.git
cd Social-Media-App
```

### 2. Configure Database
Create a MySQL database:
```sql
CREATE DATABASE social_media_app;
```

Update `src/main/resources/application.properties` with your credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/social_media_app
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build the Project
```bash
./mvnw clean install
```

### 4. Run the Application
```bash
./mvnw spring-boot:run
```

The application will start on **http://localhost:8080**

---

## 📚 API Documentation

### Users

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/users` | Create a new user | `{ "username": "john", "email": "john@example.com", "password": "pass123", "bio": "Hello" }` |
| GET | `/users` | Get all users | - |
| GET | `/users/{id}` | Get user by ID | - |
| PUT | `/users/{id}` | Update user | `{ "username": "newname", "bio": "Updated bio" }` |
| PUT | `/users/{id}/password` | Change password | `{ "oldPassword": "old123", "newPassword": "new123" }` |
| DELETE | `/users/{id}` | Delete user | - |

### Follow System

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users/{followerId}/follow/{followingId}` | Follow a user |
| DELETE | `/users/{followerId}/unfollow/{followingId}` | Unfollow a user |
| GET | `/users/{userId}/followers?page=0&size=10` | Get paginated followers |
| GET | `/users/{userId}/following?page=0&size=10` | Get paginated following |

### Posts

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/posts` | Create a post | `{ "userId": 1, "content": "Hello World!", "imageUrl": "http://..." }` |
| GET | `/posts` | Get all posts with likes, reactions, comments | - |
| GET | `/posts/{id}` | Get specific post with details | - |
| DELETE | `/posts/{id}` | Delete post (cascades to comments, likes, reactions) | - |

### Comments

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/comments` | Create a comment | `{ "postId": 1, "userId": 1, "content": "Nice post!" }` |
| GET | `/comments/post/{postId}` | Get all comments for a post | - |
| GET | `/comments/{id}` | Get comment by ID with likes/reactions | - |
| PUT | `/comments/{id}` | Update comment | `{ "content": "Updated comment" }` |
| DELETE | `/comments/{id}` | Delete comment | - |

### Likes

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/likes/post/{postId}/user/{userId}` | Like a post |
| POST | `/likes/comment/{commentId}/user/{userId}` | Like a comment |
| GET | `/likes/post/{postId}/count` | Count likes on a post |
| GET | `/likes/comment/{commentId}/count` | Count likes on a comment |
| DELETE | `/likes/post/{postId}` | Delete all likes for a post |
| DELETE | `/likes/comment/{commentId}` | Delete all likes for a comment |

### Reactions

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/reactions/post/{postId}/user/{userId}?type=LOVE` | React to a post |
| POST | `/reactions/comment/{commentId}/user/{userId}?type=HAHA` | React to a comment |
| GET | `/reactions/post/{postId}` | Get all reactions for a post |
| GET | `/reactions/comment/{commentId}` | Get all reactions for a comment |
| GET | `/reactions/post/{postId}/count` | Count reactions by type |
| GET | `/reactions/comment/{commentId}/count` | Count reactions by type |

**Reaction Types:** `LIKE`, `LOVE`, `HAHA`, `WOW`, `SAD`, `ANGRY`

---

## 🧪 Testing with cURL

### Create a User
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123","bio":"Test user"}'
```

### Create a Post
```bash
curl -X POST http://localhost:8080/posts \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"content":"My first post"}'
```

### Like a Post
```bash
curl -X POST http://localhost:8080/likes/post/1/user/1
```

### Follow a User
```bash
curl -X POST http://localhost:8080/users/1/follow/2
```

---

## 🗄️ Database Schema

### Main Tables
- **users** - User accounts with hashed passwords
- **posts** - User posts with timestamps
- **comments** - Comments on posts
- **likes** - Likes on posts and comments (with unique constraints)
- **reactions** - Emoji reactions on posts and comments
- **follows** - Follower/following relationships

### Indexes
- `idx_post_user_id` - Fast post lookups by user
- `idx_post_created_at` - Chronological post queries
- `idx_like_post_id`, `idx_like_comment_id` - Fast like counting
- `idx_user_username`, `idx_user_email` - Fast user lookups

### Constraints
- Unique constraints on likes to prevent duplicates
- Foreign keys with cascading deletes
- Email and username uniqueness

---

## 🔒 Security Features

- ✅ **BCrypt Password Hashing** - Passwords stored securely with salt
- ✅ **Password Hidden** - Passwords never returned in API responses
- ✅ **Input Validation** - Email format, password length, etc.
- ✅ **SQL Injection Protection** - JPA parameterized queries
- ✅ **CSRF Disabled** - For stateless REST API
- ✅ **JWT-Ready** - Security configuration prepared for JWT tokens

---

## 🚧 Future Enhancements

- [ ] JWT Authentication with access/refresh tokens
- [ ] User feed/timeline (posts from followed users)
- [ ] Post pagination and sorting
- [ ] Search functionality (users, posts, hashtags)
- [ ] Image upload to cloud storage
- [ ] Notifications system
- [ ] Private accounts and blocking
- [ ] Unit and integration tests
- [ ] API rate limiting
- [ ] Swagger/OpenAPI documentation

---

## 📁 Project Structure

```
src/main/java/com/socialapp/socialmedia/
├── config/
│   └── SecurityConfig.java           # Spring Security configuration
├── controller/
│   ├── UserController.java           # User endpoints
│   ├── PostController.java           # Post endpoints
│   ├── CommentController.java        # Comment endpoints
│   ├── LikeController.java           # Like endpoints
│   ├── ReactionController.java       # Reaction endpoints
│   └── FollowController.java         # Follow/unfollow endpoints
├── service/
│   ├── UserService.java              # User business logic
│   ├── PostService.java              # Post business logic
│   ├── CommentService.java           # Comment business logic
│   ├── LikeService.java              # Like business logic
│   ├── ReactionService.java          # Reaction business logic
│   └── FollowService.java            # Follow business logic
├── repository/
│   ├── UserRepository.java           # User data access
│   ├── PostRepository.java           # Post data access
│   ├── CommentRepository.java        # Comment data access
│   ├── LikeRepository.java           # Like data access
│   ├── ReactionRepository.java       # Reaction data access
│   └── FollowRepository.java         # Follow data access
├── model/
│   ├── User.java                     # User entity
│   ├── Post.java                     # Post entity
│   ├── Comment.java                  # Comment entity
│   ├── Like.java                     # Like entity
│   ├── Reaction.java                 # Reaction entity
│   ├── Follow.java                   # Follow entity
│   └── ReactionType.java             # Reaction enum
├── dto/
│   ├── UserSummaryDTO.java           # User summary response
│   ├── PostResponseDTO.java          # Post with details
│   └── CommentResponseDTO.java       # Comment with details
├── exception/
│   ├── GlobalExceptionHandler.java   # Centralized error handling
│   ├── UserNotFoundException.java
│   ├── AlreadyFollowingException.java
│   └── CannotFollowSelfException.java
└── SocialmediaApplication.java       # Main application class
```

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👤 Author

**Nemi**
- GitHub: [@nemi21](https://github.com/nemi21)

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- Baeldung Tutorials
- Stack Overflow Community

---

**⭐ If you found this project helpful, please give it a star!**