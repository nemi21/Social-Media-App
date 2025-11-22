package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.model.Like;
import com.socialapp.socialmedia.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // ---------------------- LIKE POST ------------------------
    @PostMapping("/post/{postId}/user/{userId}")
    public Like likePost(@PathVariable Long postId, @PathVariable Long userId) {
        return likeService.likePost(userId, postId);
    }

    // ---------------------- LIKE COMMENT ------------------------
    @PostMapping("/comment/{commentId}/user/{userId}")
    public Like likeComment(@PathVariable Long commentId, @PathVariable Long userId) {
        return likeService.likeComment(userId, commentId);
    }

    // ---------------------- COUNT ------------------------
    @GetMapping("/post/{postId}/count")
    public int countLikesForPost(@PathVariable Long postId) {
        return likeService.countLikesForPost(postId);
    }

    @GetMapping("/comment/{commentId}/count")
    public int countLikesForComment(@PathVariable Long commentId) {
        return likeService.countLikesForComment(commentId);
    }

    // ---------------------- DELETE ------------------------
    @DeleteMapping("/post/{postId}")
    public String deleteLikesForPost(@PathVariable Long postId) {
        likeService.deleteLikesForPost(postId);
        return "All likes for post deleted successfully";
    }

    @DeleteMapping("/comment/{commentId}")
    public String deleteLikesForComment(@PathVariable Long commentId) {
        likeService.deleteLikesForComment(commentId);
        return "All likes for comment deleted successfully";
    }
}


