package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.DTO.CommentDTO;
import co.develope.SpringSocialNetwork.entities.Comment;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CommentDTO comment){
        try {
            commentRepository.save(commentService.getCommentFromCommentDTO(comment));
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment created!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
