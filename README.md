# Social Media App

A production-ready Java Spring Boot REST API for a full-featured social media application with JWT authentication, personalized feeds, nested comments with mentions, post editing/sharing, user management, and comprehensive engagement features.

## 🚀 Features

### 🔐 Authentication & Security
- ✅ **JWT Authentication** - Secure token-based login/register system
- ✅ **Access Tokens** - 24-hour expiring tokens for API access
- ✅ **BCrypt Password Hashing** - Industry-standard password encryption
- ✅ **Protected Endpoints** - All routes require authentication (except auth endpoints)
- ✅ **Token Validation** - Automatic request interception and validation

### 👤 User Management
- ✅ **User CRUD Operations** - Create, read, update, delete users
- ✅ **User Profiles** - Profiles with post/follower/following counts
- ✅ **Input Validation** - Email format, username length, password strength
- ✅ **Search Users** - Find users by username
- ✅ **Profile Analytics** - View user statistics and activity

### 🌐 Social Features
- ✅ **Follow/Unfollow System** - Users can follow and unfollow each other
- ✅ **Paginated Followers/Following** - View followers and following lists with pagination
- ✅ **Personalized Feed** - See posts from users you follow
- ✅ **Follow Status** - Check if you're following a user
- ✅ **Duplicate Prevention** - Can't follow yourself or follow the same user twice

### 📱 Posts & Content
- ✅ **Create Posts** - Text content with optional image URLs
- ✅ **Edit Posts** - Modify your posts after creation (with ownership validation)
- ✅ **Multiple Images** - Support up to multiple images per post
- ✅ **Share/Repost** - Share posts with optional commentary
- ✅ **User Posts** - View all posts by a specific user (paginated)
- ✅ **Delete Posts** - Remove posts with cascading deletion
- ✅ **Search Posts** - Find posts by content/keywords
- ✅ **Rich Responses** - Posts include like counts, reaction counts, and comments
- ✅ **Timestamps** - Track creation and update times

### 💬 Comments & Engagement
- ✅ **Nested Comments** - Unlimited reply depth (comments on comments on comments...)
- ✅ **@Mentions** - Tag users with @username (automatically extracted)
- ✅ **Comment Threads** - View entire conversation trees
- ✅ **Edit Comments** - Update your comments
- ✅ **Delete Comments** - Remove comments with cascading deletion
- ✅ **Likes** - Like posts and comments (duplicate prevention built-in)
- ✅ **Reactions** - React with LIKE, LOVE, HAHA, WOW, SAD, ANGRY
- ✅ **Engagement Counts** - Real-time like and reaction counts

### 🔍 Discovery & Search
- ✅ **Search Users** - Find users by username
- ✅ **Search Posts** - Search posts by content
- ✅ **Paginated Results** - All lists support pagination

### ⚡ Technical Features
- ✅ **JWT Filter Chain** - Automatic token validation on every request
- ✅ **Database Optimization** - Indexes on frequently queried columns
- ✅ **Performance** - Optimized COUNT queries, connection pooling
- ✅ **Data Integrity** - Unique constraints, foreign keys, cascading deletes
- ✅ **Error Handling** - Clear, structured error responses with validation details
- ✅ **Clean Architecture** - Controller → Service → Repository pattern
- ✅ **Recursive Loading** - Efficient nested comment loading

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Security** (JWT authentication)
- **Spring Data JPA** (Hibernate)
- **MySQL 8.0**
- **Maven** (Build tool)
- **Jakarta Validation** (Input validation)
- **JJWT** (JWT library - 0.11.5)

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

### 🔐 Authentication

| Method | Endpoint | Description | Request Body | Auth Required |
|--------|----------|-------------|--------------|---------------|
| POST | `/auth/register` | Register new user | `{ "username": "john", "email": "john@example.com", "password": "pass123", "bio": "Hello" }` | No |
| POST | `/auth/login` | Login user | `{ "username": "john", "password": "pass123" }` | No |
| GET | `/auth/me` | Get current user info | - | Yes |

**Response (Login/Register):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "id": 1,
  "username": "john",
  "email": "john@example.com",
  "bio": "Hello",
  "avatarUrl": null
}
```

---

### 👤 Users

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/users` | Get all users | Yes |
| GET | `/users/{id}` | Get user by ID | Yes |
| PUT | `/users/{id}` | Update user | Yes |
| PUT | `/users/{id}/password` | Change password | Yes |
| DELETE | `/users/{id}` | Delete user | Yes |

---

### 📊 User Profiles & Activity

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/users/{userId}/profile` | Get user profile with counts | Yes |
| GET | `/api/users/{userId}/posts?page=0&size=20` | Get user's posts (paginated) | Yes |
| GET | `/api/users/search?keyword=john` | Search users by username | Yes |

**Profile Response:**
```json
{
  "id": 1,
  "username": "john",
  "email": "john@example.com",
  "bio": "Hello",
  "avatarUrl": null,
  "createdAt": "2025-11-28T...",
  "postCount": 15,
  "followerCount": 42,
  "followingCount": 38,
  "isFollowing": true
}
```

---

### 🌐 Follow System

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/users/{followerId}/follow/{followingId}` | Follow a user | Yes |
| DELETE | `/users/{followerId}/unfollow/{followingId}` | Unfollow a user | Yes |
| GET | `/users/{userId}/followers?page=0&size=10` | Get paginated followers | Yes |
| GET | `/users/{userId}/following?page=0&size=10` | Get paginated following | Yes |

---

### 📱 Posts

| Method | Endpoint | Description | Request Body | Auth Required |
|--------|----------|-------------|--------------|---------------|
| POST | `/posts` | Create a post | `{ "content": "Hello!", "imageUrls": ["url1", "url2"] }` | Yes |
| GET | `/posts` | Get all posts with details | - | Yes |
| GET | `/posts/{id}` | Get specific post | - | Yes |
| PUT | `/posts/{id}` | Edit post (owner only) | `{ "content": "Updated!", "imageUrls": ["url"] }` | Yes |
| DELETE | `/posts/{id}` | Delete post (cascades) | - | Yes |
| POST | `/posts/{id}/share` | Share/repost | `{ "content": "Check this out!" }` (optional) | Yes |
| GET | `/posts/{id}/original` | Get original post (for reposts) | - | Yes |

---

### 📰 Feed & Discovery

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/feed?page=0&size=20` | Get personalized feed (posts from followed users) | Yes |
| GET | `/api/feed/search?keyword=coding&page=0&size=10` | Search posts by content | Yes |

**Feed Response:**
```json
{
  "content": [
    {
      "id": 1,
      "userId": 2,
      "content": "Great post!",
      "imageUrls": ["https://example.com/photo.jpg"],
      "originalPostId": null,
      "likeCount": 5,
      "reactionCounts": {"LOVE": 3, "HAHA": 1},
      "comments": [...]
    }
  ],
  "totalElements": 42,
  "totalPages": 3
}
```

---

### 💬 Comments (Nested with Mentions)

| Method | Endpoint | Description | Request Body | Auth Required |
|--------|----------|-------------|--------------|---------------|
| POST | `/comments` | Create comment/reply | `{ "postId": 1, "content": "Nice! @john", "parentCommentId": 5 }` | Yes |
| GET | `/comments/post/{postId}` | Get all comments (nested) | - | Yes |
| GET | `/comments/{id}` | Get comment with replies | - | Yes |
| GET | `/comments/{id}/replies` | Get direct replies | - | Yes |
| GET | `/comments/{id}/replies/count` | Count replies | - | Yes |
| PUT | `/comments/{id}` | Update comment | `{ "content": "Updated!" }` | Yes |
| DELETE | `/comments/{id}` | Delete comment | - | Yes |

**Nested Comment Response:**
```json
{
  "id": 1,
  "content": "Great post!",
  "mentionedUsernames": [],
  "replies": [
    {
      "id": 2,
      "content": "I agree @testuser!",
      "mentionedUsernames": ["testuser"],
      "parentCommentId": 1,
      "replies": [
        {
          "id": 5,
          "content": "Me too!",
          "parentCommentId": 2,
          "replies": []
        }
      ]
    }
  ]
}
```

---

### 👍 Likes

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/likes/post/{postId}/user/{userId}` | Like a post | Yes |
| POST | `/likes/comment/{commentId}/user/{userId}` | Like a comment | Yes |
| GET | `/likes/post/{postId}/count` | Count likes on a post | Yes |
| GET | `/likes/comment/{commentId}/count` | Count likes on a comment | Yes |
| DELETE | `/likes/post/{postId}` | Delete all likes for a post | Yes |
| DELETE | `/likes/comment/{commentId}` | Delete all likes for a comment | Yes |

---

### 😍 Reactions

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/reactions/post/{postId}/user/{userId}?type=LOVE` | React to a post | Yes |
| POST | `/reactions/comment/{commentId}/user/{userId}?type=HAHA` | React to a comment | Yes |
| GET | `/reactions/post/{postId}` | Get all reactions for a post | Yes |
| GET | `/reactions/comment/{commentId}` | Get all reactions for a comment | Yes |
| GET | `/reactions/post/{postId}/count` | Count reactions by type | Yes |
| GET | `/reactions/comment/{commentId}/count` | Count reactions by type | Yes |

**Reaction Types:** `LIKE`, `LOVE`, `HAHA`, `WOW`, `SAD`, `ANGRY`

---

## 🧪 Testing Examples

### Register & Login
```bash
# Register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123","bio":"Test user"}'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Copy the token from response
TOKEN="your_token_here"
```

### Create & Edit Posts
```bash
# Create post with multiple images
curl -X POST http://localhost:8080/posts \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"content":"Check out these photos!","imageUrls":["url1","url2","url3"]}'

# Edit post
curl -X PUT http://localhost:8080/posts/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"content":"Updated content!","imageUrls":["new-url"]}'

# Share post
curl -X POST http://localhost:8080/posts/1/share \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"content":"This is amazing!"}'
```

### Nested Comments with Mentions
```bash
# Create top-level comment
curl -X POST http://localhost:8080/comments \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"postId":1,"content":"Great post @john!"}'

# Reply to comment (nested)
curl -X POST http://localhost:8080/comments \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"postId":1,"content":"I agree!","parentCommentId":1}'

# Get comment thread
curl -X GET http://localhost:8080/comments/post/1 \
  -H "Authorization: Bearer $TOKEN"
```

### Social Features
```bash
# Get personalized feed
curl -X GET http://localhost:8080/api/feed?page=0&size=20 \
  -H "Authorization: Bearer $TOKEN"

# Search users
curl -X GET "http://localhost:8080/api/users/search?keyword=test" \
  -H "Authorization: Bearer $TOKEN"

# Search posts
curl -X GET "http://localhost:8080/api/feed/search?keyword=coding" \
  -H "Authorization: Bearer $TOKEN"
```

---

## 🗄️ Database Schema

### Main Tables
- **users** - User accounts with hashed passwords
- **posts** - User posts with multiple images and repost tracking
- **comments** - Comments with parent-child relationships for nesting
- **likes** - Likes on posts and comments (with unique constraints)
- **reactions** - Emoji reactions on posts and comments
- **follows** - Follower/following relationships

### Key Indexes
- `idx_post_user_id` - Fast post lookups by user
- `idx_post_created_at` - Chronological post queries
- `idx_comment_post_id` - Fast comment lookups by post
- `idx_comment_parent_id` - Efficient nested comment queries
- `idx_like_post_id`, `idx_like_comment_id` - Fast like counting
- `idx_user_username`, `idx_user_email` - Fast user lookups

### Constraints
- Unique constraints on likes to prevent duplicates
- Foreign keys with cascading deletes
- Email and username uniqueness
- Validated parent comment relationships

---

## 🔒 Security Features

- ✅ **JWT Token Authentication** - Stateless, scalable authentication
- ✅ **BCrypt Password Hashing** - Passwords stored securely with salt
- ✅ **Password Hidden in Responses** - Passwords never returned in API responses
- ✅ **Token Expiration** - Tokens expire after 24 hours (configurable)
- ✅ **Request Filter Chain** - Automatic token validation on every request
- ✅ **Ownership Validation** - Users can only edit their own posts/comments
- ✅ **Input Validation** - Comprehensive validation with clear error messages
- ✅ **SQL Injection Protection** - JPA parameterized queries
- ✅ **CSRF Disabled** - For stateless REST API

---

## 🚧 Upcoming Features

- [ ] Real-time notifications system (likes, comments, mentions, follows)
- [ ] Trending posts algorithm
- [ ] Popular users discovery
- [ ] Private accounts (approve followers)
- [ ] Block users functionality
- [ ] Image upload to cloud storage (AWS S3/Cloudinary)
- [ ] Unit and integration tests
- [ ] API rate limiting
- [ ] Swagger/OpenAPI documentation
- [ ] WebSocket support for real-time updates

---

## 📁 Project Structure

```
src/main/java/com/socialapp/socialmedia/
├── config/
│   └── SecurityConfig.java              # JWT Security configuration
├── controller/
│   ├── AuthController.java              # Login/Register endpoints
│   ├── UserController.java              # User CRUD endpoints
│   ├── UserProfileController.java       # Profile & search endpoints
│   ├── PostController.java              # Post CRUD/Edit/Share endpoints
│   ├── FeedController.java              # Feed & search endpoints
│   ├── CommentController.java           # Comment/Reply endpoints
│   ├── LikeController.java              # Like endpoints
│   ├── ReactionController.java          # Reaction endpoints
│   └── FollowController.java            # Follow/unfollow endpoints
├── service/
│   ├── UserService.java                 # User business logic
│   ├── CustomUserDetailsService.java    # Spring Security user loading
│   ├── FeedService.java                 # Feed and search logic
│   ├── PostService.java                 # Post/Edit/Share logic
│   ├── CommentService.java              # Comment/Reply logic (recursive)
│   ├── LikeService.java                 # Like business logic
│   ├── ReactionService.java             # Reaction business logic
│   └── FollowService.java               # Follow business logic
├── repository/
│   ├── UserRepository.java              # User data access
│   ├── PostRepository.java              # Post data access (with feed queries)
│   ├── CommentRepository.java           # Comment data access (nested queries)
│   ├── LikeRepository.java              # Like data access
│   ├── ReactionRepository.java          # Reaction data access
│   └── FollowRepository.java            # Follow data access (with counts)
├── model/
│   ├── User.java                        # User entity
│   ├── Post.java                        # Post entity (multi-image, repost)
│   ├── Comment.java                     # Comment entity (nested, mentions)
│   ├── Like.java                        # Like entity
│   ├── Reaction.java                    # Reaction entity
│   ├── Follow.java                      # Follow entity
│   └── ReactionType.java                # Reaction enum
├── dto/
│   ├── LoginRequest.java                # Login request DTO
│   ├── LoginResponse.java               # Login response DTO
│   ├── RegisterRequest.java             # Register request DTO
│   ├── CreatePostRequest.java           # Post creation DTO
│   ├── UpdatePostRequest.java           # Post update DTO
│   ├── CreateCommentRequest.java        # Comment/Reply creation DTO
│   ├── UserProfileDTO.java              # User profile with counts
│   ├── UserSummaryDTO.java              # User summary response
│   ├── PostResponseDTO.java             # Post with details
│   └── CommentResponseDTO.java          # Comment with nested replies
├── security/
│   ├── JwtUtil.java                     # JWT token generation/validation
│   └── JwtAuthenticationFilter.java     # JWT request filter
├── exception/
│   ├── GlobalExceptionHandler.java      # Centralized error handling
│   ├── UserNotFoundException.java
│   ├── AlreadyFollowingException.java
│   └── CannotFollowSelfException.java
└── SocialmediaApplication.java          # Main application class
```

---

## 🎯 For Frontend Developers

This API is fully ready for frontend integration (React, Vue, Angular, etc.):

### Authentication Flow
1. User registers/logs in via `/auth/register` or `/auth/login`
2. Store the returned JWT token in localStorage
3. Include token in all subsequent requests: `Authorization: Bearer <token>`
4. Token expires after 24 hours - handle token refresh/re-login

### Key Endpoints for Frontend
- **User Feed:** `GET /api/feed` - Homepage feed with posts from followed users
- **User Profile:** `GET /api/users/{id}/profile` - Profile page with stats
- **User Posts:** `GET /api/users/{id}/posts` - User's post list
- **Search:** `GET /api/users/search?keyword=` and `GET /api/feed/search?keyword=`
- **Create Post:** `POST /posts` - New post form (supports multiple images)
- **Edit Post:** `PUT /posts/{id}` - Edit post modal
- **Share Post:** `POST /posts/{id}/share` - Repost functionality
- **Nested Comments:** `GET /comments/post/{postId}` - Comment thread tree
- **Reply to Comment:** `POST /comments` with `parentCommentId` - Reply functionality
- **Follow:** `POST /users/{followerId}/follow/{followingId}` - Follow button

### Example React Integration
```javascript
// Login
const response = await fetch('http://localhost:8080/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ username, password })
});
const { token } = await response.json();
localStorage.setItem('token', token);

// Fetch personalized feed
const feedResponse = await fetch('http://localhost:8080/api/feed?page=0&size=20', {
  headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
});
const feed = await feedResponse.json();

// Create comment with mention
await fetch('http://localhost:8080/comments', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    postId: 1,
    content: 'Great post @username!',
    parentCommentId: null // or comment ID for reply
  })
});

// Render nested comments recursively
function CommentTree({ comment }) {
  return (
    <div className="comment">
      <p>{comment.content}</p>
      <p>Mentions: {comment.mentionedUsernames.join(', ')}</p>
      {comment.replies && comment.replies.map(reply =>
        <div className="reply">
          <CommentTree comment={reply} />
        </div>
      )}
    </div>
  );
}
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
- Spring Security Documentation
- JWT (JJWT) Library
- Baeldung Tutorials
- Stack Overflow Community

---

## 📞 Support

If you have any questions or run into issues:
1. Check existing issues on GitHub
2. Create a new issue with detailed information
3. Contact via GitHub profile

---

**⭐ If you found this project helpful, please give it a star!**

---

## 🏆 Features Highlights

### What Makes This API Special:

1. **Production-Ready Security** - JWT authentication with proper password hashing
2. **Infinite Nested Comments** - Like Reddit/Twitter comment threads
3. **Smart @Mentions** - Automatic username extraction
4. **Multi-Image Posts** - Support for photo galleries
5. **Repost Functionality** - Share posts with commentary
6. **Personalized Feeds** - See only what matters to you
7. **Comprehensive Search** - Find users and posts easily
8. **Post Editing** - Fix typos after posting
9. **Ownership Validation** - Can only edit your own content
10. **Optimized Performance** - Database indexes and efficient queries
11. **Frontend-Ready** - Clean API design perfect for React/Vue/Angular

### Current Statistics:
- 50+ API Endpoints
- 8 Core Entities
- 4 Engagement Features
- Infinite Comment Depth
- Full CRUD Operations
- JWT Security
- Pagination Support
- Search Functionality

---

**Built with ❤️ using Spring Boot**