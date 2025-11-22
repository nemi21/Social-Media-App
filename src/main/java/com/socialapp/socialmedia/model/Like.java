package com.socialapp.socialmedia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_like_user_post", columnNames = {"user_id", "post_id"}),
        @UniqueConstraint(name = "uk_like_user_comment", columnNames = {"user_id", "comment_id"})
    },
    indexes = {
        @Index(name = "idx_like_post_id", columnList = "post_id"),
        @Index(name = "idx_like_comment_id", columnList = "comment_id"),
        @Index(name = "idx_like_user_id", columnList = "user_id")
    }
)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private LocalDateTime createdAt = LocalDateTime.now();

    // ===== Constructors =====
    public Like() {}

    public Like(User user, Post post, Comment comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    // ===== Getters and Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public Comment getComment() { return comment; }
    public void setComment(Comment comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}


