package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.CommentResponseDTO;
import com.socialapp.socialmedia.dto.PostResponseDTO;
import com.socialapp.socialmedia.model.Post;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.repository.PostRepository;
import com.socialapp.socialmedia.service.LikeService;
import com.socialapp.socialmedia.service.ReactionService;
import com.socialapp.socialmedia.repository.CommentRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeService likeService;
    private final ReactionService reactionService;

    public PostController(PostRepository postRepository, CommentRepository commentRepository,
                          LikeService likeService, ReactionService reactionService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeService = likeService;
        this.reactionService = reactionService;
    }

    // Create a new post
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    // GET all posts with likes, reactions, and comments
    @GetMapping
    public List<PostResponseDTO> getAllPostsDTO() {
        return postRepository.findAll().stream()
                .map(post -> {
                    int likeCount = likeService.countLikesForPost(post.getId());
                    Map<String, Integer> reactionCounts = reactionService.countReactionsForPostByType(post.getId());
                    
                    List<Comment> comments = commentRepository.findByPostId(post.getId());
                    List<CommentResponseDTO> commentDTOs = comments.stream()
                            .map(c -> new CommentResponseDTO(
                                    c,
                                    likeService.countLikesForComment(c.getId()),
                                    reactionService.countReactionsForCommentByType(c.getId())
                            ))
                            .collect(Collectors.toList());

                    return new PostResponseDTO(post, likeCount, reactionCounts, commentDTOs);
                })
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public PostResponseDTO getPostById(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        int likeCount = likeService.countLikesForPost(post.getId());
        Map<String, Integer> reactionCounts = reactionService.countReactionsForPostByType(post.getId());

        List<CommentResponseDTO> commentDTOs = commentRepository.findByPostId(post.getId()).stream()
                .map(c -> new CommentResponseDTO(
                        c,
                        likeService.countLikesForComment(c.getId()),
                        reactionService.countReactionsForCommentByType(c.getId())
                ))
                .toList();

        return new PostResponseDTO(post, likeCount, reactionCounts, commentDTOs);
    }


    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 1️⃣ Delete likes & reactions for all comments of this post
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        for (Comment c : comments) {
            likeService.deleteLikesForComment(c.getId());
            reactionService.deleteReactionsForComment(c.getId());
        }

        // 2️⃣ Delete comments themselves
        commentRepository.deleteAll(comments);

        // 3️⃣ Delete likes and reactions for the post itself
        likeService.deleteLikesForPost(post.getId());
        reactionService.deleteReactionsForPost(post.getId());

        // 4️⃣ Delete the post
        postRepository.delete(post);

        return "Post deleted successfully";
    }

}



