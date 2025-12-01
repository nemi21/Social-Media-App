package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.CreatePostRequest;
import com.socialapp.socialmedia.dto.PostResponseDTO;
import com.socialapp.socialmedia.dto.UpdatePostRequest;
import com.socialapp.socialmedia.model.Post;
import com.socialapp.socialmedia.security.JwtUtil;
import com.socialapp.socialmedia.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    public PostController(PostService postService, JwtUtil jwtUtil) {
        this.postService = postService;
        this.jwtUtil = jwtUtil;
    }

    // -------- CREATE POST (with multiple images) ----------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(
            @Valid @RequestBody CreatePostRequest request,
            @RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        
        return postService.createPostFromRequest(request, userId);
    }

    // -------- GET ALL POSTS ----------
    @GetMapping
    public List<PostResponseDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    // -------- GET POST BY ID ----------
    @GetMapping("/{id}")
    public PostResponseDTO getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // -------- UPDATE POST ----------
    @PutMapping("/{id}")
    public Post updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request,
            @RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        
        return postService.updatePost(id, request, userId);
    }

    // -------- DELETE POST ----------
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    // -------- SHARE/REPOST ----------
    @PostMapping("/{id}/share")
    @ResponseStatus(HttpStatus.CREATED)
    public Post sharePost(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body,
            @RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        
        String additionalContent = body != null ? body.get("content") : null;
        return postService.sharePost(id, additionalContent, userId);
    }

    // -------- GET ORIGINAL POST (for reposts) ----------
    @GetMapping("/{id}/original")
    public Post getOriginalPost(@PathVariable Long id) {
        Post original = postService.getOriginalPost(id);
        if (original == null) {
            throw new RuntimeException("This post is not a repost");
        }
        return original;
    }
}




