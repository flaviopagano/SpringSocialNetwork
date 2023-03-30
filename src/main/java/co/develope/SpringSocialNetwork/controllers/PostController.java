package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @PostMapping("/create")
    public ResponseEntity createPost(@RequestBody PostDTO post){
        try {
            postRepository.save(postService.getPostFromPostDTO(post));
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    @GetMapping("/text-of-post-from-user")
    public List<String> getPost(@RequestParam Integer userId){
        return postRepository.findByUser_id(userId);
    }


}
