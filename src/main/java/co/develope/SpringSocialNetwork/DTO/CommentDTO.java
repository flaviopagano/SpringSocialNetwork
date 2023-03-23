package co.develope.SpringSocialNetwork.DTO;

public class CommentDTO {

    private String text;
    private String username;
    private int postId;

    public CommentDTO(){}

    public CommentDTO(String text, String username, int postId) {
        this.text = text;
        this.username = username;
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
