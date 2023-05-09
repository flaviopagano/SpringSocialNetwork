package co.develope.SpringSocialNetwork.entities;

import co.develope.SpringSocialNetwork.enums.ReactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post postToReact;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userWhoReacts;

    public Reaction() {
    }

    public Reaction(Post post, User user, ReactionType reactionType){
        super();
        this.postToReact = post;
        this.userWhoReacts = user;
        this.reactionType = reactionType;
    }

    public int getId() {
        return id;
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

    public Reaction setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
        return null;
    }

}
