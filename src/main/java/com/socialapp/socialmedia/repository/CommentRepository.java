package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    // Get all comments for a post
    List<Comment> findByPostId(Long postId);
    
    // ✅ NEW: Get top-level comments (no parent)
    List<Comment> findByPostIdAndParentCommentIdIsNull(Long postId);
    
    // ✅ NEW: Get replies to a specific comment
    List<Comment> findByParentCommentId(Long parentCommentId);
    
    // ✅ NEW: Count replies
    int countByParentCommentId(Long parentCommentId);
}

