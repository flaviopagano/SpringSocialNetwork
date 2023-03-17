package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @PostMapping("/add")
    public String addPost(@RequestBody User user, @RequestParam String msgText){
        return msgText;
    }







}
