package com.socialapp.socialmedia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(indexes = {
    @Index(name = "idx_comment_post_id", columnList = "postId"),
    @Index(name = "idx_comment_parent_id", columnList = "parentCommentId")
})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
    private Long userId;
    
    @Column(length = 2000)
    private String content;

    // ✅ NEW: Support for nested comments (replies)
    private Long parentCommentId; // If this is a reply, reference to parent comment

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    // ===== Constructors =====
    public Comment() {}

    public Comment(Long postId, Long userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    public Comment(Long postId, Long userId, String content, Long parentCommentId) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    // ===== Lifecycle Callbacks =====
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ===== Helper Methods for @mentions =====
    @Transient
    public List<String> getMentionedUsernames() {
        List<String> mentions = new ArrayList<>();
        if (content == null) return mentions;
        
        // Regex to find @username patterns
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            mentions.add(matcher.group(1)); // Get username without @
        }
        
        return mentions;
    }

    // ===== Getters and setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(Long parentCommentId) { this.parentCommentId = parentCommentId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}



