package com.socialapp.socialmedia.dto;

import com.socialapp.socialmedia.model.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CommentResponseDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private Long parentCommentId;
    private int likeCount;
    private Map<String, Integer> reactionCounts;
    private List<String> mentionedUsernames;
    private List<CommentResponseDTO> replies; // ✅ NEW: Nested replies
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDTO(Comment comment, int likeCount, Map<String, Integer> reactionCounts) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.postId = comment.getPostId();
        this.content = comment.getContent();
        this.parentCommentId = comment.getParentCommentId();
        this.likeCount = likeCount;
        this.reactionCounts = reactionCounts;
        this.mentionedUsernames = comment.getMentionedUsernames();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(Long parentCommentId) { this.parentCommentId = parentCommentId; }

    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

    public Map<String, Integer> getReactionCounts() { return reactionCounts; }
    public void setReactionCounts(Map<String, Integer> reactionCounts) { this.reactionCounts = reactionCounts; }

    public List<String> getMentionedUsernames() { return mentionedUsernames; }
    public void setMentionedUsernames(List<String> mentionedUsernames) { this.mentionedUsernames = mentionedUsernames; }

    public List<CommentResponseDTO> getReplies() { return replies; }
    public void setReplies(List<CommentResponseDTO> replies) { this.replies = replies; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

