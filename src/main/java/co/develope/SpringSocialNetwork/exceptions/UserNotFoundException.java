package co.develope.SpringSocialNetwork.exceptions;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message){
        super(message);
    }

    public void getMessage(String not_found) {
    }
}
