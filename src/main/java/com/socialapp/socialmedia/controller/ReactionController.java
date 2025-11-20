package com.socialapp.socialmedia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialapp.socialmedia.model.Reaction;
import com.socialapp.socialmedia.model.ReactionType;
import com.socialapp.socialmedia.service.ReactionService;

@RestController
@RequestMapping("/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }
    

    @PostMapping("/post/{postId}/user/{userId}")
    public Reaction reactToPost(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @RequestParam ReactionType type
    ) {
        return reactionService.reactToPost(userId, postId, type);
    }

    @PostMapping("/comment/{commentId}/user/{userId}")
    public Reaction reactToComment(
            @PathVariable Long commentId,
            @PathVariable Long userId,
            @RequestParam ReactionType type
    ) {
        return reactionService.reactToComment(userId, commentId, type);
    }
    
 // GET all reactions for a post
    @GetMapping("/post/{postId}")
    public List<Reaction> getReactionsForPost(@PathVariable Long postId) {
        return reactionService.getReactionsForPost(postId);
    }

    // GET all reactions for a comment
    @GetMapping("/comment/{commentId}")
    public List<Reaction> getReactionsForComment(@PathVariable Long commentId) {
        return reactionService.getReactionsForComment(commentId);
    }

    // Optional: count reactions for a post
    @GetMapping("/post/{postId}/count")
    public int countReactionsForPost(@PathVariable Long postId) {
        return reactionService.countReactionsForPost(postId);
    }

    // Optional: count reactions for a comment
    @GetMapping("/comment/{commentId}/count")
    public int countReactionsForComment(@PathVariable Long commentId) {
        return reactionService.countReactionsForComment(commentId);
    }

}

