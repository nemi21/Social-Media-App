package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // Create comment
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    // Get comments by post
    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // Delete comment
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        if (!commentRepository.existsById(id)) {
            return "Comment not found";
        }
        commentRepository.deleteById(id);
        return "Comment deleted successfully";
    }
}

