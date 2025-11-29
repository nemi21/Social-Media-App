package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.PostResponseDTO;
import com.socialapp.socialmedia.security.JwtUtil;
import com.socialapp.socialmedia.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;
    private final JwtUtil jwtUtil;

    public FeedController(FeedService feedService, JwtUtil jwtUtil) {
        this.feedService = feedService;
        this.jwtUtil = jwtUtil;
    }

    // Get personalized feed
    @GetMapping
    public ResponseEntity<Page<PostResponseDTO>> getFeed(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        
        Page<PostResponseDTO> feed = feedService.getUserFeed(userId, page, size);
        return ResponseEntity.ok(feed);
    }

    // Search posts
    @GetMapping("/search")
    public ResponseEntity<Page<PostResponseDTO>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Page<PostResponseDTO> posts = feedService.searchPosts(keyword, page, size);
        return ResponseEntity.ok(posts);
    }
}