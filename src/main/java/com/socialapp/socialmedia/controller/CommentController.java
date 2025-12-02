package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.CommentResponseDTO;
import com.socialapp.socialmedia.dto.CreateCommentRequest;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.security.JwtUtil;
import com.socialapp.socialmedia.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    public CommentController(CommentService commentService, JwtUtil jwtUtil) {
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }

    // -------- CREATE COMMENT (or reply) ----------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(
            @Valid @RequestBody CreateCommentRequest request,
            @RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        
        return commentService.createCommentFromRequest(request, userId);
    }

    // -------- GET COMMENTS FOR POST (with nested replies) ----------
    @GetMapping("/post/{postId}")
    public List<CommentResponseDTO> getCommentsForPost(@PathVariable Long postId) {
        return commentService.getCommentsWithReplies(postId);
    }

    // -------- GET COMMENT BY ID ----------
    @GetMapping("/{id}")
    public CommentResponseDTO getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getComment(id);

        CommentResponseDTO dto = new CommentResponseDTO(
                comment,
                commentService.getLikeService().countLikesForComment(comment.getId()),
                commentService.getReactionService().countReactionsForCommentByType(comment.getId())
        );
        
        // Load replies
        dto.setReplies(commentService.getRepliesForComment(comment.getId()));
        return dto;
    }

    // -------- GET REPLIES FOR A COMMENT ----------
    @GetMapping("/{id}/replies")
    public List<CommentResponseDTO> getReplies(@PathVariable Long id) {
        return commentService.getRepliesForComment(id);
    }

    // -------- COUNT REPLIES ----------
    @GetMapping("/{id}/replies/count")
    public Map<String, Integer> countReplies(@PathVariable Long id) {
        int count = commentService.countReplies(id);
        return Map.of("replyCount", count);
    }

    // -------- UPDATE COMMENT ----------
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newContent = body.get("content");
        if (newContent == null || newContent.isBlank()) {
            throw new RuntimeException("Content must not be empty");
        }
        return commentService.updateComment(id, newContent);
    }

    // -------- DELETE COMMENT ----------
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}

