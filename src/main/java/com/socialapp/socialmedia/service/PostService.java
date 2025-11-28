package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.dto.CommentResponseDTO;
import com.socialapp.socialmedia.dto.PostResponseDTO;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.model.Post;
import com.socialapp.socialmedia.repository.CommentRepository;
import com.socialapp.socialmedia.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeService likeService;
    private final ReactionService reactionService;

    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository,
                       LikeService likeService,
                       ReactionService reactionService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeService = likeService;
        this.reactionService = reactionService;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return mapToDTO(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Delete likes & reactions for each comment
        List<Comment> comments = commentRepository.findByPostId(postId);
        for (Comment c : comments) {
            likeService.deleteLikesForComment(c.getId());
            reactionService.deleteReactionsForComment(c.getId());
        }

        // Delete comments themselves
        commentRepository.deleteAll(comments);

        // Delete likes & reactions for the post
        likeService.deleteLikesForPost(postId);
        reactionService.deleteReactionsForPost(postId);

        // Finally delete post
        postRepository.delete(post);
    }

    // -------- Helper to map Post → PostResponseDTO ----------
    public PostResponseDTO mapToDTO(Post post) {
        int likeCount = likeService.countLikesForPost(post.getId());
        Map<String, Integer> reactionCounts =
                reactionService.countReactionsForPostByType(post.getId());

        List<CommentResponseDTO> commentDTOs = commentRepository.findByPostId(post.getId()).stream()
                .map(c -> new CommentResponseDTO(
                        c,
                        likeService.countLikesForComment(c.getId()),
                        reactionService.countReactionsForCommentByType(c.getId())
                ))
                .toList();

        return new PostResponseDTO(post, likeCount, reactionCounts, commentDTOs);
    }
}

