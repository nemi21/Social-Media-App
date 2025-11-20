package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.Reaction;
import com.socialapp.socialmedia.model.Comment;
import com.socialapp.socialmedia.model.Post;
import com.socialapp.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByUserAndPost(User user, Post post);
    Optional<Reaction> findByUserAndComment(User user, Comment comment);

    List<Reaction> findByPostId(Long postId);
    List<Reaction> findByCommentId(Long commentId);

    int countByPostId(Long postId);
    int countByCommentId(Long commentId);
}

