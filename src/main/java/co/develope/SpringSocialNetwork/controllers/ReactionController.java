package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.ReactionDTO;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.ReactionRepository;
import co.develope.SpringSocialNetwork.services.ReactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reaction")
public class ReactionController {

    Logger logger = LoggerFactory.getLogger(ReactionController.class);

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private ReactionRepository reactionRepository;

    @PostMapping("/create/loving")
    public ResponseEntity createLovingReaction (@RequestBody ReactionDTO reaction) {
        try {
            logger.info("Loving reaction added!");
            reactionService.addLovingReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/angry")
    public ResponseEntity createAngryReaction (@RequestBody ReactionDTO reaction) {
        try {
            logger.info("Angry reaction added!");
            reactionService.addAngryReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/hate")
    public ResponseEntity createHateReaction (@RequestBody ReactionDTO reaction) {
        try {
            logger.info("Hate reaction added!");
            reactionService.addHateReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/praying")
    public ResponseEntity createPrayingReaction (@RequestBody ReactionDTO reaction) {
        try {
            logger.info("Praying reaction added!");
            reactionService.addPrayingReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/happy")
    public ResponseEntity createHappyReaction (@RequestBody ReactionDTO reaction) {
        try {
            logger.info("Happy reaction added!");
            reactionService.addHappyReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/sad")
    public ResponseEntity createSadReaction (@RequestBody ReactionDTO reaction) {
        try {
            logger.info("Sad reaction added!");
            reactionService.addSadReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/crying")
    public ResponseEntity createCryingReaction (@RequestBody ReactionDTO reaction) {
        try {
            logger.info("Crying reaction added!");
            reactionService.addCryingReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-reactions/{id}")
    public ResponseEntity getAllReactionFromPost(@PathVariable int postId){
        try {
            logger.info("Getting all reactions from a post");
            reactionService.getAllReactionsFromPost(postId);
            return ResponseEntity.status(HttpStatus.OK).body("Post's reactions");
        } catch (PostNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-reaction/{id}")
    public ResponseEntity deleteReactionById(@PathVariable Integer id){
        logger.info("User want to delete reaction with id " + id);
        reactionService.deleteReactionById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Reaction deleted!");
    }
}
