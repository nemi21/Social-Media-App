package com.socialapp.socialmedia.repository;

import com.socialapp.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Optional: add method to find by username or email
    User findByUsername(String username);
    User findByEmail(String email);
}

