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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    Logger logger = LoggerFactory.getLogger(PostController.class);

     @PostMapping("/create")
     public ResponseEntity createPost(@RequestBody PostDTO postDTO){
         try {
             logger.info("Creating post");
             return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postDTO));
         } catch (UserNotFoundException e) {
             logger.info(e.getMessage());
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }

    @PostMapping(value = "/create-with-img", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createPostWithIMG(@RequestPart PostDTO postDTO, @RequestPart MultipartFile image){
        try {
            logger.info("Creating a post with one image");
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPostWithImage(postDTO,image));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-post-by-id")
    public ResponseEntity getPostById(@RequestParam Integer postId){
        try {
            logger.info("Getting post with id " + postId);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId));
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-post-text-by-id")
    public ResponseEntity getPostTextById(@RequestParam Integer postId){
        try {
            logger.info("Getting text of the post with id " + postId);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId).getText());
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/get-post-image-by-id", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity getPostImageById(@RequestParam Integer postId){
        try {
            logger.info("Getting image of the post with id " + postId);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostImageById(postId));
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-user")
    public ResponseEntity getUserWhoPosts(@PathVariable Integer id){
        try {
            logger.info("Getting user of the post with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id).getUserWhoPosts());
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-publication-date")
    public ResponseEntity getPublicationDate(@PathVariable Integer id){
        try {
            logger.info("Getting publication date of the post with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id).getPublicationDate());
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-update-date")
    public ResponseEntity getUpdateDate(@PathVariable Integer id){
        try {
            logger.info("Getting update date of the post with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id).getUpdateDate());
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-comments")
    public ResponseEntity getComments(@PathVariable Integer id){
        try {
            logger.info("Getting comments of the post with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id).getComments());
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-reactions")
    public ResponseEntity getReactions(@PathVariable Integer id){
        try {
            logger.info("Getting reactions of the post with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id).getReactions());
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-by-user-id")
    public ResponseEntity getAllPostsFromUser(@RequestParam Integer userId){
        try {
            logger.info("Getting all posts from user with id: " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsFromUserId(userId));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-all")
    public List<Post> getPostById(){
        logger.info("Getting all posts");
        return postService.getAllPosts();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deletePost(@RequestParam Integer postId){
        try {
            logger.info("Trying to delete post by id");
            postService.deletePostById(postId);
            return ResponseEntity.status(200).body("Post deleted successfully");
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/edit-text-by-id")
    public ResponseEntity editPost(@RequestParam Integer postId, @RequestParam String text){
        try{
        logger.info("Editing post");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(postService.editPostTextById(postId,text));
        }catch (PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //da fare edit image of the post

}
