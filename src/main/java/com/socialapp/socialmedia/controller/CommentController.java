package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.CommentResponseDTO;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // -------------------- CREATE --------------------

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    // -------------------- READ --------------------

    @GetMapping("/post/{postId}")
    public List<CommentResponseDTO> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @GetMapping("/{id}")
    public CommentResponseDTO getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getComment(id);

        return new CommentResponseDTO(
                comment,
                commentService.getLikeService().countLikesForComment(comment.getId()),
                commentService.getReactionService().countReactionsForCommentByType(comment.getId())
        );
    }

 // -------------------- UPDATE --------------------
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Map<String, String> body) {
        // Expect JSON: { "content": "new comment text" }
        String newContent = body.get("content");
        if (newContent == null || newContent.isBlank()) {
            throw new RuntimeException("Content must not be empty");
        }
        return commentService.updateComment(id, newContent);
    }

    // -------------------- DELETE --------------------
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteComment(@PathVariable Long id) {
        Comment comment = commentService.getComment(id); // throws exception if not found
        commentService.deleteComment(id);
        return Map.of(
            "message", "Comment deleted successfully",
            "deletedCommentId", comment.getId(),
            "content", comment.getContent()
        );
    }

}

