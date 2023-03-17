package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    /** vanno collegati i post all'user (user non ancora disponibile) **/

    @PostMapping("/add")
    public Post createPost(@RequestBody Post post){
        return postRepository.save(post);
    }

    @GetMapping("/get-posts")
    public List<Post> getPosts(){
        return postRepository.findAll();
    }





}
