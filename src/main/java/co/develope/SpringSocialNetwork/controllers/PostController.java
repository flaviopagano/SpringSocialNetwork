package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

     @PostMapping("/create")
     public ResponseEntity createPost(@RequestBody PostDTO postDTO){
         try {
             postRepository.save(postService.createPost(postDTO));
             return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully!");
         } catch (UserNotFoundException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }

    @PostMapping(value = "/create-with-img", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createPostWithIMG(@RequestPart PostDTO postDTO, @RequestPart MultipartFile image){
        try {
            postRepository.save(postService.createPostWithImage(postDTO,image));
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("image not found");
        }
    }


    @GetMapping("/get-post-by-id")
    public ResponseEntity getPostById(@RequestParam Integer postId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId));
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public List<Post> getPostById(){
        return postRepository.findAll();
    }


    @GetMapping("/get-all-by-user-id")
    public ResponseEntity getAllPostsFromUser(@RequestParam Integer userId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsFromUserId(userId));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

/*    @DeleteMapping("/delete")
    public ResponseEntity deletePost(@RequestParam Integer postId){
        return postService.deletePostById(postId);
    } */

    @PutMapping("/edit-text-by-id")
    public ResponseEntity editPost(@RequestParam Integer postId, @RequestBody PostDTO postDTO){
        return postService.editPostById(postId,postDTO);
    }


}
