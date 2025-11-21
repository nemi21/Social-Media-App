package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.FollowUserDTO;
import com.socialapp.socialmedia.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<String> follow(@PathVariable Long followerId, @PathVariable Long followingId) {
        followService.followUser(followerId, followingId);
        return ResponseEntity.ok("Followed successfully");
    }

    @DeleteMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity<String> unfollow(@PathVariable Long followerId, @PathVariable Long followingId) {
        followService.unfollowUser(followerId, followingId);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowUserDTO>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowersDTO(userId));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<FollowUserDTO>> getFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowingDTO(userId));
    }
}


