package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    List<User> UserList = new ArrayList<>();

    @Autowired
    UserService userService;

    @PostMapping ("/create")
    public String createUser(@RequestBody User user){
        User newUser = new User();
        UserList.add(newUser);
      return newUser.toString();

    }

    @GetMapping("/details")
    public String UserDetails(){
        return userService.UserDetails();
    }

}
