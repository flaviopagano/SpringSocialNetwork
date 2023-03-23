package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @PostMapping("/create")
    public Post createPost(@RequestBody PostDTO post){
        return postRepository.save(new Post());
    }

    @GetMapping("/get-all-posts")
    public List<Post> getPosts(){
        return postRepository.findAll();
    }

}
