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

    @PostMapping("/post/{postId}/user/{userId}")
    public Like likePost(@PathVariable Long postId, @PathVariable Long userId) {
        return likeService.likePost(userId, postId);
    }

    @PostMapping("/comment/{commentId}/user/{userId}")
    public Like likeComment(@PathVariable Long commentId, @PathVariable Long userId) {
        return likeService.likeComment(userId, commentId);
    }

    @GetMapping("/post/{postId}/count")
    public int getPostLikes(@PathVariable Long postId) {
        return likeService.countLikesForPost(postId);
    }

    @GetMapping("/comment/{commentId}/count")
    public int getCommentLikes(@PathVariable Long commentId) {
        return likeService.countLikesForComment(commentId);
    }
}

