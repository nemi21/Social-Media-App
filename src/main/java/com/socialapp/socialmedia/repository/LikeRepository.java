package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndComment(User user, Comment comment);

    List<Like> findByPost(Post post);

    List<Like> findByComment(Comment comment);
}

