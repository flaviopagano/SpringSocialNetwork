package co.develope.SpringSocialNetwork.entities.DTO;

public class PostDTO {
    private String text;
    private String username;
    private byte[] postImage;

    public PostDTO(){}


    public PostDTO(String text, String username, byte[] postImage) {
        this.text = text;
        this.username = username;
        this.postImage = postImage;
    }

    public PostDTO(String text, String username) {
        this.text = text;
        this.username = username;
    }

    public PostDTO(String text) {
        this.text = text;
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

    public byte[] getPostImage() {
        return postImage;
    }

    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }
}
