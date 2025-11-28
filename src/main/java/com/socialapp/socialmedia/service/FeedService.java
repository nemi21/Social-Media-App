package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.dto.PostResponseDTO;
import com.socialapp.socialmedia.model.Post;
import com.socialapp.socialmedia.repository.FollowRepository;
import com.socialapp.socialmedia.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final PostService postService;

    public FeedService(PostRepository postRepository, FollowRepository followRepository, PostService postService) {
        this.postRepository = postRepository;
        this.followRepository = followRepository;
        this.postService = postService;
    }

    // Get personalized feed for user
    public Page<PostResponseDTO> getUserFeed(Long userId, int page, int size) {
        // Get list of users that this user follows
        List<Long> followingIds = followRepository.findFollowingIdsByUserId(userId);
        
        // Add the user's own posts to the feed
        followingIds.add(userId);
        
        // If user doesn't follow anyone, return empty feed
        if (followingIds.isEmpty()) {
            return Page.empty();
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findFeedPosts(followingIds, pageable);
        
        // Convert to DTOs
        return posts.map(post -> postService.mapToDTO(post));
    }

    // Get posts by a specific user
    public Page<PostResponseDTO> getUserPosts(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return posts.map(post -> postService.mapToDTO(post));
    }

    // Search posts
    public Page<PostResponseDTO> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.searchByContent(keyword, pageable);
        return posts.map(post -> postService.mapToDTO(post));
    }
}
