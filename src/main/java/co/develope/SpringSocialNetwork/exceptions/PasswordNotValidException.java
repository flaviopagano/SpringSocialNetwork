package co.develope.SpringSocialNetwork.exceptions;

public class PasswordNotValidException extends Exception{
    @Override
    public String getMessage() {
        return "Password not valid!";
    }
}
