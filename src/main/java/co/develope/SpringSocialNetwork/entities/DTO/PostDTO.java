package co.develope.SpringSocialNetwork.entities.DTO;

public class PostDTO {
    private String text;
    private String username;

    public PostDTO(){}

    public PostDTO(String text, String username) {
        this.text = text;
        this.username = username;
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

}
