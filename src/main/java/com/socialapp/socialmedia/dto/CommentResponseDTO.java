package com.socialapp.socialmedia.dto;

import com.socialapp.socialmedia.model.Comment;
import java.util.Map;

public class CommentResponseDTO {
    private Long id;
    private Long userId;
    private String content;
    private int likeCount;
    private Map<String, Integer> reactionCounts; // e.g., {"LOVE":1,"HAHA":0}

    public CommentResponseDTO(Comment comment, int likeCount, Map<String, Integer> reactionCounts) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.likeCount = likeCount;
        this.reactionCounts = reactionCounts;
    }

    // Getters and setters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public int getLikeCount() { return likeCount; }
    public Map<String, Integer> getReactionCounts() { return reactionCounts; }
}

