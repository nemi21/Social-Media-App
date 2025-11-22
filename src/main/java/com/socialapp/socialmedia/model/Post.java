package com.socialapp.socialmedia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts",
    indexes = {
        @Index(name = "idx_post_user_id", columnList = "userId"),
        @Index(name = "idx_post_created_at", columnList = "createdAt")
    }
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Content cannot be empty")
    @Size(min = 1, max = 5000, message = "Content must be between 1 and 5000 characters")
    @Column(length = 5000)
    private String content;

    private String imageUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;

    // ===== Constructors =====
    public Post() {}

    public Post(Long userId, String content, String imageUrl) {
        this.userId = userId;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    // ===== Lifecycle Callbacks =====
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ===== Getters and setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}


