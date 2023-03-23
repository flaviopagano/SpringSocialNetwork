package co.develope.SpringSocialNetwork.exceptions;

public class EmailAlreadyPresentException extends Exception{

    @Override
    public String getMessage() {
        return "Email already present!";
    }
}
