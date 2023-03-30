package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.Comment;
import co.develope.SpringSocialNetwork.entities.DTO.CommentDTO;
import co.develope.SpringSocialNetwork.exceptions.CommentNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CommentDTO comment){
        try {
            commentService.createComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment created!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-comment/{id}")
    public ResponseEntity getCommentById(@PathVariable Integer id){
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getCommentById(id));
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-comment-from-user")
    public ResponseEntity getAllCommentsFromAUser(@RequestParam Integer userId){
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getAllCommentsFromUser(userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-comment-from-post")
    public ResponseEntity getAllCommentsFromAPost(@RequestParam Integer postId){
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(commentService.getAllCommentsFromPost(postId));
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSingleComment(@PathVariable Integer id){
        return commentService.deleteCommentById(id);
    }

}
