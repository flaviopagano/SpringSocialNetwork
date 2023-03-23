package co.develope.SpringSocialNetwork.exceptions;

public class UserNotFoundException extends Exception{

    @Override
    public String getMessage() {
        return "User not found";
    }
}
