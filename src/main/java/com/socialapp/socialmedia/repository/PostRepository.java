package com.socialapp.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socialapp.socialmedia.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    // Get posts by a specific user (paginated)
    Page<Post> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    // Count posts by user
    int countByUserId(Long userId);
    
    // Get feed: posts from users that the given user follows
    @Query("SELECT p FROM Post p WHERE p.userId IN :followingIds ORDER BY p.createdAt DESC")
    Page<Post> findFeedPosts(@Param("followingIds") List<Long> followingIds, Pageable pageable);
    
    // Search posts by content (case-insensitive)
    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.createdAt DESC")
    Page<Post> searchByContent(@Param("keyword") String keyword, Pageable pageable);
}

