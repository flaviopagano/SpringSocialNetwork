package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.exceptions.IdNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/find-all-by-id")
    public List<String> getAllUserPosts(@RequestParam Integer userId) throws IdNotFoundException {
        List<String> empty = new ArrayList<>();
        try {
            return postService.getAllPostsFromId(userId);
        }catch (IdNotFoundException e){
            e.getMessage();
        }
        return empty;
    }
    @DeleteMapping("/delete")
    public ResponseEntity deletePost(@RequestParam Integer postId){
        return postService.deletePostById(postId);
    }
    @PutMapping("/edit")
    public ResponseEntity editPost(@RequestParam Integer postId, @RequestBody PostDTO postDTO){
        return postService.editPostById(postId,postDTO);
    }


}
