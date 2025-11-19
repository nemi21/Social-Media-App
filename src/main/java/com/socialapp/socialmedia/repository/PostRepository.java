package com.socialapp.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.socialapp.socialmedia.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}

