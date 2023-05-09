package co.develope.SpringSocialNetwork.entities.DTO;

public class ReactionDTO {

    private String username;
    private int postId;

    public ReactionDTO() {
    }

    public ReactionDTO(String username, int postId){
        this.username = username;
        this.postId = postId;
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
