package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.PostResponseDTO;
import com.socialapp.socialmedia.dto.UserProfileDTO;
import com.socialapp.socialmedia.model.User;
import com.socialapp.socialmedia.security.JwtUtil;
import com.socialapp.socialmedia.service.FeedService;
import com.socialapp.socialmedia.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserService userService;
    private final FeedService feedService;
    private final JwtUtil jwtUtil;

    public UserProfileController(UserService userService, FeedService feedService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.feedService = feedService;
        this.jwtUtil = jwtUtil;
    }

    // Get user profile with counts
    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile(
            @PathVariable Long userId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        Long currentUserId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            currentUserId = jwtUtil.extractUserId(token);
        }
        
        UserProfileDTO profile = userService.getUserProfile(userId, currentUserId);
        return ResponseEntity.ok(profile);
    }

    // Get user's posts (paginated)
    @GetMapping("/{userId}/posts")
    public ResponseEntity<Page<PostResponseDTO>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Page<PostResponseDTO> posts = feedService.getUserPosts(userId, page, size);
        return ResponseEntity.ok(posts);
    }

    // Search users by username
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        List<User> users = userService.searchUsers(keyword);
        return ResponseEntity.ok(users);
    }
}
