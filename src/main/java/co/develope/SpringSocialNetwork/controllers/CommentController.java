package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.Comment;
import co.develope.SpringSocialNetwork.entities.DTO.CommentDTO;
import co.develope.SpringSocialNetwork.exceptions.CommentNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CommentDTO comment){
        try {
            logger.info("Creating comment");
            commentService.createComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment created!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-comment/{id}")
    public ResponseEntity getCommentById(@PathVariable Integer id){
        try {
            logger.info("Getting comment by id");
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getCommentById(id));
        } catch (CommentNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-comment-from-user")
    public ResponseEntity getAllCommentsFromAUser(@RequestParam Integer userId){
        try {
            logger.info("Getting all comments from user");
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getAllCommentsFromUser(userId));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-comment-from-post")
    public ResponseEntity getAllCommentsFromAPost(@RequestParam Integer postId){
        try {
            logger.info("Getting all comments from post");
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getAllCommentsFromPost(postId));
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Comment> getAllComments(){
        logger.info("Getting all comments");
        return commentRepository.findAll();
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateComment(@PathVariable Integer id, @RequestParam String text){
        try {
            logger.info("Updating comment");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentService.updateComment(id, text));
        } catch (CommentNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSingleComment(@PathVariable Integer id){
        try {
            logger.info("Deleting comment");
            return commentService.deleteCommentById(id);
        } catch (CommentNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-publication-date")
    public ResponseEntity getPublicationDate(@PathVariable Integer id){
        try {
            logger.info("Getting publication date");
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getCommentById(id).getPublicationDate());
        } catch (CommentNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-update-date")
    public ResponseEntity getUpdateDate(@PathVariable Integer id){
        try {
            logger.info("Getting update date");
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getCommentById(id).getUpdateDate());
        } catch (CommentNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-user")
    public ResponseEntity getUserWhoComments(@PathVariable Integer id){
        try {
            logger.info("Getting user who commented");
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getCommentById(id).getUserWhoComments());
        } catch (CommentNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-post")
    public ResponseEntity getPostCommented(@PathVariable Integer id){
        try {
            logger.info("Getting post commented");
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getCommentById(id).getPostToComment());
        } catch (CommentNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
