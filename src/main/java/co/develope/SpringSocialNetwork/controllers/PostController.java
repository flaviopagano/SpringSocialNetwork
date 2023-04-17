package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(PostController.class);

     @PostMapping("/create")
     public ResponseEntity createPost(@RequestBody PostDTO postDTO){
         try {
             postRepository.save(postService.createPost(postDTO));
             logger.info("Creating post");
             return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully!");
         } catch (UserNotFoundException e) {
             logger.info(e.getMessage());
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }

    @PostMapping(value = "/create-with-img", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createPostWithIMG(@RequestPart PostDTO postDTO, @RequestPart MultipartFile image){
        try {
            postRepository.save(postService.createPostWithImage(postDTO,image));
            logger.info("Creating a post with an image");
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException("image not found");
        }
    }


    @GetMapping("/get-post-by-id")
    public ResponseEntity getPostById(@RequestParam Integer postId){
        try {
            logger.info("Getting comment with id "+postId);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId));
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public List<Post> getPostById(){
         logger.info("Getting all posts");
        return postRepository.findAll();
    }


    @GetMapping("/get-all-by-user-id")
    public ResponseEntity getAllPostsFromUser(@RequestParam Integer userId){
        try {
            logger.info("Getting all posts from user with id: "+userId);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsFromUserId(userId));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);
        }
    }

/*    @DeleteMapping("/delete")
    public ResponseEntity deletePost(@RequestParam Integer postId){
        return postService.deletePostById(postId);
    } */

    @PutMapping("/edit-text-by-id")
    public ResponseEntity editPost(@RequestParam Integer postId, @RequestBody String text){
        try{
        logger.info("Editing post");
        return postService.editPostById(postId,text);
        }catch (PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
