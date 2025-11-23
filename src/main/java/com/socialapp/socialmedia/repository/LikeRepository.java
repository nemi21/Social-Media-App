package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.Like;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.model.Post;
import com.socialapp.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // Find likes by post/comment
    List<Like> findByPost(Post post);
    List<Like> findByComment(Comment comment);

    // Check if user already liked
    Optional<Like> findByUserAndPost(User user, Post post);
    Optional<Like> findByUserAndComment(User user, Comment comment);

    // ✅ OPTIMIZED: Use COUNT instead of loading all records
    int countByPostId(Long postId);
    int countByCommentId(Long commentId);

    // Delete methods
    void deleteByCommentId(Long commentId);
    void deleteByPostId(Long postId);
}


