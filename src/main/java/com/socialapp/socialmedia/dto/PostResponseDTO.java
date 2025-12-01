package com.socialapp.socialmedia.dto;

import com.socialapp.socialmedia.model.Post;
import java.util.List;
import java.util.Map;

public class PostResponseDTO {
    private Long id;
    private Long userId;
    private String content;
    private List<String> imageUrls;
    private Long originalPostId;
    private int likeCount;
    private Map<String, Integer> reactionCounts;
    private List<CommentResponseDTO> comments;

    public PostResponseDTO(Post post, int likeCount, Map<String, Integer> reactionCounts, List<CommentResponseDTO> comments) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.content = post.getContent();
        this.imageUrls = post.getImageUrlList();
        this.originalPostId = post.getOriginalPostId();
        this.likeCount = likeCount;
        this.reactionCounts = reactionCounts;
        this.comments = comments;
    }

    // Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public List<String> getImageUrls() { return imageUrls; }
    public Long getOriginalPostId() { return originalPostId; }
    public int getLikeCount() { return likeCount; }
    public Map<String, Integer> getReactionCounts() { return reactionCounts; }
    public List<CommentResponseDTO> getComments() { return comments; }
}
