package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.dto.CommentResponseDTO;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.repository.CommentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReactionService reactionService;
    private final LikeService likeService;

    public CommentService(CommentRepository commentRepository,
                          ReactionService reactionService,
                          LikeService likeService) {
        this.commentRepository = commentRepository;
        this.reactionService = reactionService;
        this.likeService = likeService;
    }
    
    public ReactionService getReactionService() {
        return reactionService;
    }

    public LikeService getLikeService() {
        return likeService;
    }


    // ---------------------- CREATE ------------------------

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // ---------------------- READ ------------------------

    public List<CommentResponseDTO> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(c -> new CommentResponseDTO(
                        c,
                        likeService.countLikesForComment(c.getId()),
                        reactionService.countReactionsForCommentByType(c.getId())
                ))
                .toList();
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
    }


 // ---------------------- UPDATE ------------------------
    public Comment updateComment(Long id, String newContent) {
        Comment comment = getComment(id);
        comment.setContent(newContent.trim()); // remove extra quotes/spaces
        return commentRepository.save(comment);
    }


    // ---------------------- DELETE ------------------------

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found: " + id);
        }

        // Delete dependent reactions and likes first
        reactionService.deleteReactionsForComment(id);
        likeService.deleteLikesForComment(id);

        // Then delete the comment itself
        commentRepository.deleteById(id);
    }
}

