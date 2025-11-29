package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    
    List<Follow> findByFollowerId(Long followerId);
    List<Follow> findByFollowingId(Long followingId);
    
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
    
    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);
    
    // Count followers
    int countByFollowingId(Long userId);
    
    // Count following
    int countByFollowerId(Long userId);
    
    // Get list of user IDs that a user follows (for feed)
    @Query("SELECT f.following.id FROM Follow f WHERE f.follower.id = :userId")
    List<Long> findFollowingIdsByUserId(@Param("userId") Long userId);
}

