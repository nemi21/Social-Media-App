package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.model.*;
import com.socialapp.socialmedia.repository.*;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository,
                       PostRepository postRepository, CommentRepository commentRepository) {

        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Like likePost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        likeRepository.findByUserAndPost(user, post)
                .ifPresent(l -> { throw new RuntimeException("Already liked"); });

        Like like = new Like(user, post, null);
        return likeRepository.save(like);
    }

    public Like likeComment(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        likeRepository.findByUserAndComment(user, comment)
                .ifPresent(l -> { throw new RuntimeException("Already liked"); });

        Like like = new Like(user, null, comment);
        return likeRepository.save(like);
    }

    public int countLikesForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return likeRepository.findByPost(post).size();
    }

    public int countLikesForComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return likeRepository.findByComment(comment).size();
    }

    public void deleteLikesForComment(Long commentId) {
        likeRepository.deleteByCommentId(commentId);
    }
    
 // Delete likes for a specific post
    public void deleteLikesForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        likeRepository.deleteByPostId(post.getId());
    }

}
