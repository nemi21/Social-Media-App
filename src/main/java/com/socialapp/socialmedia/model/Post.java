package com.socialapp.socialmedia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // ✅ CHANGED: Support multiple images (stored as comma-separated URLs)
    @Column(length = 2000)
    private String imageUrls; // e.g., "url1,url2,url3"

    // ✅ NEW: Track if this is a shared/reposted post
    private Long originalPostId; // If this is a repost, reference to original

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    // ===== Constructors =====
    public Post() {}

    public Post(Long userId, String content, String imageUrls) {
        this.userId = userId;
        this.content = content;
        this.imageUrls = imageUrls;
    }

    // ===== Lifecycle Callbacks =====
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ===== Helper Methods for Multiple Images =====
    @Transient
    public List<String> getImageUrlList() {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return new ArrayList<>();
        }
        return List.of(imageUrls.split(","));
    }

    public void setImageUrlList(List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            this.imageUrls = null;
        } else {
            this.imageUrls = String.join(",", urls);
        }
    }

    // ===== Getters and setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrls() { return imageUrls; }
    public void setImageUrls(String imageUrls) { this.imageUrls = imageUrls; }

    public Long getOriginalPostId() { return originalPostId; }
    public void setOriginalPostId(Long originalPostId) { this.originalPostId = originalPostId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

