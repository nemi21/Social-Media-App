package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.Like;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByPost(Post post);

    List<Like> findByComment(Comment comment);

    Optional<Like> findByUserAndPost(com.socialapp.socialmedia.model.User user, Post post);

    Optional<Like> findByUserAndComment(com.socialapp.socialmedia.model.User user, Comment comment);

    // ✅ Delete all likes for a given comment
    void deleteByCommentId(Long commentId);

    // ✅ Delete all likes for a given post
    void deleteByPostId(Long postId);
}


