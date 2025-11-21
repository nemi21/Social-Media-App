package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.CommentResponseDTO;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.repository.CommentRepository;
import com.socialapp.socialmedia.service.LikeService;
import com.socialapp.socialmedia.service.ReactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final LikeService likeService;
    private final ReactionService reactionService;

    public CommentController(CommentRepository commentRepository,
                             LikeService likeService,
                             ReactionService reactionService) {
        this.commentRepository = commentRepository;
        this.likeService = likeService;
        this.reactionService = reactionService;
    }

    // Create comment
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    // Get comments by post with likes & reactions
    @GetMapping("/post/{postId}")
    public List<CommentResponseDTO> getCommentsByPost(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(c -> new CommentResponseDTO(
                        c,
                        likeService.countLikesForComment(c.getId()),
                        reactionService.countReactionsForCommentByType(c.getId())
                ))
                .toList();
    }

    // Delete comment
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        if (!commentRepository.existsById(id)) {
            return "Comment not found";
        }

        // Delete likes associated with the comment first
        likeService.deleteLikesForComment(id);

        // Delete reactions associated with the comment first
        reactionService.deleteReactionsForComment(id);

        // Now delete the comment itself
        commentRepository.deleteById(id);

        return "Comment deleted successfully";
    }

}
