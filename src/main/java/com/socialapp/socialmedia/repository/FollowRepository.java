package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long followerId);
    List<Follow> findByFollowingId(Long followingId);
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
}

