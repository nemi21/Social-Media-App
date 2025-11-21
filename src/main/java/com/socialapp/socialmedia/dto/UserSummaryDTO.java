package com.socialapp.socialmedia.dto;

public class UserSummaryDTO {
    private Long id;
    private String username;
    private String bio;
    private String avatarUrl;

    public UserSummaryDTO(Long id, String username, String bio, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public String getAvatarUrl() { return avatarUrl; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setBio(String bio) { this.bio = bio; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}

