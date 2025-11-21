package com.socialapp.socialmedia.service;

import com.socialapp.socialmedia.dto.FollowUserDTO;
import com.socialapp.socialmedia.exception.*;
import com.socialapp.socialmedia.model.Follow;
import com.socialapp.socialmedia.model.User;
import com.socialapp.socialmedia.repository.FollowRepository;
import com.socialapp.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public void followUser(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new CannotFollowSelfException("Cannot follow yourself");
        }

        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new AlreadyFollowingException("You are already following this user");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new UserNotFoundException("User to follow not found"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);
    }

    public void unfollowUser(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerId(followerId).stream()
                .filter(f -> f.getFollowing().getId().equals(followingId))
                .findFirst()
                .orElseThrow(() -> new FollowNotFoundException("Follow relationship not found"));
        followRepository.delete(follow);
    }

    public List<FollowUserDTO> getFollowersDTO(Long userId) {
        return followRepository.findByFollowingId(userId)
                .stream()
                .map(f -> new FollowUserDTO(
                        f.getFollower().getId(),
                        f.getFollower().getUsername()
                ))
                .toList();
    }

    public List<FollowUserDTO> getFollowingDTO(Long userId) {
        return followRepository.findByFollowerId(userId)
                .stream()
                .map(f -> new FollowUserDTO(
                        f.getFollowing().getId(),
                        f.getFollowing().getUsername()
                ))
                .toList();
    }
}

