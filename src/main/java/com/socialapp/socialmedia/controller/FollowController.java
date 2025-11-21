package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.UserSummaryDTO;
import com.socialapp.socialmedia.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Paginated followers
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Page<UserSummaryDTO>> getFollowers(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(followService.getFollowersPaginated(userId, page, size));
    }

    // Paginated following
    @GetMapping("/{userId}/following")
    public ResponseEntity<Page<UserSummaryDTO>> getFollowing(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(followService.getFollowingPaginated(userId, page, size));
    }
}



