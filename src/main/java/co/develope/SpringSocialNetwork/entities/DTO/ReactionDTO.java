package co.develope.SpringSocialNetwork.entities.DTO;

import co.develope.SpringSocialNetwork.enums.ReactionType;

public class ReactionDTO {

    private ReactionType reactionType;
    private String username;
    private int postId;

    public ReactionDTO() {
    }

    public ReactionDTO(ReactionType reactionType, String username, int postId) {
        this.reactionType = reactionType;
        this.username = username;
        this.postId = postId;
    }

    public ReactionDTO(String username, int postId){
        this.username = username;
        this.postId = postId;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
