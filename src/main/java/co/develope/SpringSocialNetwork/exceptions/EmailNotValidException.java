package co.develope.SpringSocialNetwork.exceptions;

public class EmailNotValidException extends Exception{
    @Override
    public String getMessage() {
        return "Email not valid!";
    }
}
