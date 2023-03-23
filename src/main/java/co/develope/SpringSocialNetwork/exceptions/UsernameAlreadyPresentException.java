package co.develope.SpringSocialNetwork.exceptions;

public class UsernameAlreadyPresentException extends Exception{

    @Override
    public String getMessage() {
        return "Username already present!";
    }
}
