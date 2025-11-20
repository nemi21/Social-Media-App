package com.socialapp.socialmedia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReactionType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Reaction() {}

    public Reaction(ReactionType type, User user, Post post, Comment comment) {
        this.type = type;
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public ReactionType getType() { return type; }
    public void setType(ReactionType type) { this.type = type; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
    public Comment getComment() { return comment; }
    public void setComment(Comment comment) { this.comment = comment; }
}

