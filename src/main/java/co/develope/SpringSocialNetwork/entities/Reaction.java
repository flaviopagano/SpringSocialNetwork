package co.develope.SpringSocialNetwork.entities;

import co.develope.SpringSocialNetwork.enums.ReactionType;

import javax.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction extends BaseEntity{
    @Column(nullable = false)
    private ReactionType reactionType;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postToReact;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userWhoReacts;

    public Reaction(Post post, User user, ReactionType reactionType){
        this.postToReact = post;
        this.userWhoReacts = user;
        this.reactionType = reactionType;
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
