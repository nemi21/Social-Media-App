package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.Reaction;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    List<Reaction> findByPostId(Long postId);

    List<Reaction> findByCommentId(Long commentId);

    Optional<Reaction> findByUserAndPost(com.socialapp.socialmedia.model.User user, Post post);

    Optional<Reaction> findByUserAndComment(com.socialapp.socialmedia.model.User user, Comment comment);

    int countByPostId(Long postId);

    int countByCommentId(Long commentId);

    // ✅ Delete all reactions for a given comment
    void deleteByCommentId(Long commentId);

    // ✅ Delete all reactions for a given post
    void deleteByPostId(Long postId);
}


