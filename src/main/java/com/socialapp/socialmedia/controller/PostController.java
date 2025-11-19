package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.model.Post;
import com.socialapp.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    // Create a new post
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    // Get all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Delete a post by id
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return "Post not found";
        }
        postRepository.deleteById(id);
        return "Post deleted successfully";
    }
}


