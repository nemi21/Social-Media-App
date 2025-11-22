package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.model.*;
import com.socialapp.socialmedia.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public LikeService(LikeRepository likeRepository,
                       UserRepository userRepository,
                       PostRepository postRepository,
                       CommentRepository commentRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // ---------------------- LIKE POST ------------------------
    public Like likePost(Long userId, Long postId) {
        User user = getUser(userId);
        Post post = getPost(postId);

        // Check if user already liked this post
        likeRepository.findByUserAndPost(user, post)
                .ifPresent(l -> { throw new RuntimeException("User already liked this post"); });

        Like like = new Like(user, post, null);
        return likeRepository.save(like);
    }

    // ---------------------- LIKE COMMENT ------------------------
    public Like likeComment(Long userId, Long commentId) {
        User user = getUser(userId);
        Comment comment = getComment(commentId);

        // Check if user already liked this comment
        likeRepository.findByUserAndComment(user, comment)
                .ifPresent(l -> { throw new RuntimeException("User already liked this comment"); });

        Like like = new Like(user, null, comment);
        return likeRepository.save(like);
    }

    // ---------------------- COUNT ------------------------
    public int countLikesForPost(Long postId) {
        Post post = getPost(postId);
        return likeRepository.findByPost(post).size();
    }

    public int countLikesForComment(Long commentId) {
        Comment comment = getComment(commentId);
        return likeRepository.findByComment(comment).size();
    }

    // ---------------------- DELETE ------------------------
    public void deleteLikesForPost(Long postId) {
        Post post = getPost(postId);
        likeRepository.deleteByPostId(post.getId());
    }

    public void deleteLikesForComment(Long commentId) {
        Comment comment = getComment(commentId);
        likeRepository.deleteByCommentId(comment.getId());
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

