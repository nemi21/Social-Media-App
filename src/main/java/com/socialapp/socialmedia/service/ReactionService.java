package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.model.*;
import com.socialapp.socialmedia.repository.*;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public ReactionService(ReactionRepository reactionRepository, UserRepository userRepository,
                           PostRepository postRepository, CommentRepository commentRepository) {
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Reaction reactToPost(Long userId, Long postId, ReactionType type) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Reaction reaction = reactionRepository.findByUserAndPost(user, post)
                .orElse(new Reaction(type, user, post, null));

        reaction.setType(type);
        return reactionRepository.save(reaction);
    }

    public Reaction reactToComment(Long userId, Long commentId, ReactionType type) {
        User user = userRepository.findById(userId).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        Reaction reaction = reactionRepository.findByUserAndComment(user, comment)
                .orElse(new Reaction(type, user, null, comment));

        reaction.setType(type);
        return reactionRepository.save(reaction);
    }
    
 // ✅ New GET methods
    public List<Reaction> getReactionsForPost(Long postId) {
        return reactionRepository.findByPostId(postId);
    }

    public List<Reaction> getReactionsForComment(Long commentId) {
        return reactionRepository.findByCommentId(commentId);
    }

    public int countReactionsForPost(Long postId) {
        return reactionRepository.countByPostId(postId);
    }

    public int countReactionsForComment(Long commentId) {
        return reactionRepository.countByCommentId(commentId);
    }
}

