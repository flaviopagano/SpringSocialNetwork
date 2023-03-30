package co.develope.SpringSocialNetwork.exceptions;

public class IdNotFoundException extends Exception{

    @Override
    public String getMessage() {
        return "Id not present";
    }


}
