package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.services.FileStorageService;
import co.develope.SpringSocialNetwork.services.PostService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;

    @Autowired
    FileStorageService fileStorageService;

     @PostMapping
     public ResponseEntity createPost(@RequestBody PostDTO postDTO){
         try {
             logger.info("Creating post");
             postService.createPost(postDTO);
             return ResponseEntity.status(HttpStatus.CREATED).body("Post created");
         } catch (UserNotFoundException e) {
             logger.info(e.getMessage());
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }

    @PostMapping(value = "/with-image", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createPostWithIMG(@RequestPart PostDTO postDTO, @RequestPart MultipartFile image){
        try {
            logger.info("Creating a post with one image");
            postService.createPostWithImage(postDTO,image);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post with image created");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPostById(@PathVariable Integer id){
        try {
            logger.info("Getting post with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-text")
    public ResponseEntity getPostTextById(@PathVariable Integer id){
        try {
            logger.info("Getting text of the post with id " + id);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id).getText());
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}/get-image", method = RequestMethod.GET, produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity getPostImageById(@PathVariable Integer id, HttpServletResponse response){
        try {
            logger.info("Retrieving the image from post with id " + id);
            Post myPost = postService.getPostById(id);
            logger.info("Getting image of the post with id " + id);
            String extension = FilenameUtils.getExtension(myPost.getImages());
            switch(extension){
                case "gif" :
                    response.setContentType(MediaType.IMAGE_GIF_VALUE);
                    break;
                case "jpg" : case "jpeg" :
                    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                    break;
                case "png" :
                    response.setContentType(MediaType.IMAGE_PNG_VALUE);
                    break;
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + myPost.getImages());
            return ResponseEntity.status(HttpStatus.OK).body(fileStorageService.download(myPost.getImages(), true));
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

    //metodo inutile, più facile farlo dall'user
    /*@GetMapping("/get-all-by-user-id")
    public ResponseEntity getAllPostsFromUser(@RequestParam Integer userId){
        try {
            logger.info("Getting all posts from user with id: " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsFromUserId(userId));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);
        }
    }*/

    @GetMapping
    public List<Post> getPostById(){
        logger.info("Getting all posts");
        return postService.getAllPosts();
    }

    @PutMapping("/{id}/edit-text")
    public ResponseEntity editPostText(@PathVariable Integer id, @RequestParam String text){
        try{
        logger.info("Editing post text");
        postService.editPostTextById(id,text);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Text of the post with id " + id + " edited");
        }catch (PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}/edit-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity editPostImage(@PathVariable Integer id, @RequestPart MultipartFile image){
        try{
            logger.info("Editing post image");
            postService.editPostImageById(id, image);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Image of the post with id " + id + " edited");
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Integer id){
        try {
            logger.info("Trying to delete post by id");
            postService.deletePostById(id);
            return ResponseEntity.status(200).body("Post deleted successfully");
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
