package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.dto.CommentResponseDTO;
import com.socialapp.socialmedia.dto.CreateCommentRequest;
import java.util.ArrayList;
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
    
 // -------- CREATE COMMENT (with reply support) ----------
    public Comment createCommentFromRequest(CreateCommentRequest request, Long userId) {
        // Validate parent comment exists if this is a reply
        if (request.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found: " + request.getParentCommentId()));
            
            // Ensure reply is on same post as parent
            if (!parentComment.getPostId().equals(request.getPostId())) {
                throw new RuntimeException("Reply must be on the same post as parent comment");
            }
        }
        
        Comment comment = new Comment();
        comment.setPostId(request.getPostId());
        comment.setUserId(userId);
        comment.setContent(request.getContent());
        comment.setParentCommentId(request.getParentCommentId());
        
        return commentRepository.save(comment);
    }

    // -------- GET COMMENTS WITH NESTED REPLIES ----------
    public List<CommentResponseDTO> getCommentsWithReplies(Long postId) {
        // Get only top-level comments (no parent)
        List<Comment> topLevelComments = commentRepository.findByPostIdAndParentCommentIdIsNull(postId);
        
        List<CommentResponseDTO> result = new ArrayList<>();
        for (Comment comment : topLevelComments) {
            CommentResponseDTO dto = new CommentResponseDTO(
                comment,
                likeService.countLikesForComment(comment.getId()),
                reactionService.countReactionsForCommentByType(comment.getId())
            );
            
            // Load replies recursively
            dto.setReplies(getRepliesForComment(comment.getId()));
            result.add(dto);
        }
        
        return result;
    }

    // -------- GET REPLIES FOR A COMMENT (recursive) ----------
    public List<CommentResponseDTO> getRepliesForComment(Long commentId) {
        List<Comment> replies = commentRepository.findByParentCommentId(commentId);
        
        List<CommentResponseDTO> result = new ArrayList<>();
        for (Comment reply : replies) {
            CommentResponseDTO dto = new CommentResponseDTO(
                reply,
                likeService.countLikesForComment(reply.getId()),
                reactionService.countReactionsForCommentByType(reply.getId())
            );
            
            // Recursively load nested replies
            dto.setReplies(getRepliesForComment(reply.getId()));
            result.add(dto);
        }
        
        return result;
    }

    // -------- COUNT REPLIES ----------
    public int countReplies(Long commentId) {
        return commentRepository.countByParentCommentId(commentId);
    }
}

