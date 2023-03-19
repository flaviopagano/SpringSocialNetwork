package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {


    User user;



    public String UserDetails(){
     return  user.toString();
    }

}



