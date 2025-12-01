package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.dto.CommentResponseDTO;
import com.socialapp.socialmedia.dto.CreatePostRequest;
import com.socialapp.socialmedia.dto.UpdatePostRequest;
import com.socialapp.socialmedia.security.JwtUtil;
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
    
 // -------- CREATE with DTO ----------
    public Post createPostFromRequest(CreatePostRequest request, Long userId) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(request.getContent());
        post.setImageUrlList(request.getImageUrls());
        return postRepository.save(post);
    }

    // -------- UPDATE POST ----------
    @Transactional
    public Post updatePost(Long postId, UpdatePostRequest request, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));
        
        // Check if user owns this post
        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You can only edit your own posts");
        }
        
        post.setContent(request.getContent());
        post.setImageUrlList(request.getImageUrls());
        return postRepository.save(post);
    }

    // -------- SHARE/REPOST ----------
    @Transactional
    public Post sharePost(Long originalPostId, String additionalContent, Long userId) {
        Post originalPost = postRepository.findById(originalPostId)
                .orElseThrow(() -> new RuntimeException("Original post not found: " + originalPostId));
        
        Post sharedPost = new Post();
        sharedPost.setUserId(userId);
        sharedPost.setOriginalPostId(originalPostId);
        
        // Combine additional content with reference to original
        String content = additionalContent != null && !additionalContent.isEmpty() 
            ? additionalContent + "\n\n[Shared from @user" + originalPost.getUserId() + "]"
            : "[Shared from @user" + originalPost.getUserId() + "]\n\n" + originalPost.getContent();
        
        sharedPost.setContent(content);
        sharedPost.setImageUrlList(originalPost.getImageUrlList());
        
        return postRepository.save(sharedPost);
    }

    // -------- GET ORIGINAL POST (if it's a repost) ----------
    public Post getOriginalPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));
        
        if (post.getOriginalPostId() != null) {
            return postRepository.findById(post.getOriginalPostId())
                    .orElse(null);
        }
        return null;
    }
}

