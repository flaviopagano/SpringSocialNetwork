package co.develope.SpringSocialNetwork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class  Comment extends BaseEntity{

    @Column(nullable = false)
    private String description;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userWhoComments;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post postToComment;

    public Comment(){}

    public Comment(String description, User userWhoComments, Post postToComment) {
        super();
        this.description = description;
        this.userWhoComments = userWhoComments;
        this.postToComment = postToComment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUserWhoComments() {
        return userWhoComments;
    }

    public void setUserWhoComments(User userWhoComments) {
        this.userWhoComments = userWhoComments;
    }

    public Post getPostToComment() {
        return postToComment;
    }

    public void setPostToComment(Post postToComment) {
        this.postToComment = postToComment;
    }
}
