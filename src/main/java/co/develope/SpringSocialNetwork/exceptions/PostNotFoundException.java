package co.develope.SpringSocialNetwork.exceptions;

public class PostNotFoundException extends Exception{

    @Override
    public String getMessage() {
        return "Post not found";
    }
}
