package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.model.*;
import com.socialapp.socialmedia.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public ReactionService(ReactionRepository reactionRepository,
                           UserRepository userRepository,
                           PostRepository postRepository,
                           CommentRepository commentRepository) {
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // ---------------------- CREATE OR UPDATE REACTION ------------------------

    public Reaction reactToPost(Long userId, Long postId, ReactionType type) {
        User user = getUser(userId);
        Post post = getPost(postId);

        // If already reacted, update the type
        Reaction reaction = reactionRepository.findByUserAndPost(user, post)
                .orElse(new Reaction(type, user, post, null));

        reaction.setType(type);
        return reactionRepository.save(reaction);
    }

    public Reaction reactToComment(Long userId, Long commentId, ReactionType type) {
        User user = getUser(userId);
        Comment comment = getComment(commentId);

        Reaction reaction = reactionRepository.findByUserAndComment(user, comment)
                .orElse(new Reaction(type, user, null, comment));

        reaction.setType(type);
        return reactionRepository.save(reaction);
    }

    // ---------------------- GETTERS ------------------------

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

    public Map<String, Integer> countReactionsForPostByType(Long postId) {
        return reactionRepository.findByPostId(postId)
                .stream()
                .collect(Collectors.groupingBy(
                        r -> r.getType().name(),
                        Collectors.summingInt(r -> 1)
                ));
    }

    public Map<String, Integer> countReactionsForCommentByType(Long commentId) {
        return reactionRepository.findByCommentId(commentId)
                .stream()
                .collect(Collectors.groupingBy(
                        r -> r.getType().name(),
                        Collectors.summingInt(r -> 1)
                ));
    }

    // ---------------------- DELETE ------------------------

    public void deleteReactionsForComment(Long commentId) {
        reactionRepository.deleteByCommentId(commentId);
    }

    public void deleteReactionsForPost(Long postId) {
        reactionRepository.deleteByPostId(postId);
    }

    // ---------------------- VALIDATION HELPERS ------------------------

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    private Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
    }
}


