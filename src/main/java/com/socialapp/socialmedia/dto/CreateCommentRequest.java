package com.socialapp.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateCommentRequest {

    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotBlank(message = "Content cannot be empty")
    @Size(min = 1, max = 2000, message = "Content must be between 1 and 2000 characters")
    private String content;

    private Long parentCommentId; // Optional: for replies

    // Constructors
    public CreateCommentRequest() {}

    public CreateCommentRequest(Long postId, String content, Long parentCommentId) {
        this.postId = postId;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    // Getters and Setters
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(Long parentCommentId) { this.parentCommentId = parentCommentId; }
}
