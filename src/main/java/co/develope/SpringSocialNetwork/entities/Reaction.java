package co.develope.SpringSocialNetwork.entities;

import co.develope.SpringSocialNetwork.entities.enums.ReactionType;

import javax.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue
    private String reactionId;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postToReact;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userWhoReacts;
    private ReactionType reactionType;

    public Reaction(Post post, User user, ReactionType reactionType){
        this.postToReact = post;
        this.userWhoReacts = user;
        this.reactionType = reactionType;
    }

    public String getReactionId() {
        return reactionId;
    }

    public void setReactionId(String reactionId) {
        this.reactionId = reactionId;
    }

    public Post getPostToReact() {
        return postToReact;
    }

    public void setPostToReact(Post postToReact) {
        this.postToReact = postToReact;
    }

    public User getUserWhoReacts() {
        return userWhoReacts;
    }

    public void setUserWhoReacts(User userWhoReacts) {
        this.userWhoReacts = userWhoReacts;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

}
