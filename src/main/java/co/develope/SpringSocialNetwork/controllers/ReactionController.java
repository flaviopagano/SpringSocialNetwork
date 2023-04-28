package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.ReactionDTO;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.ReactionNotFoundException;
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

    @PostMapping("/loving")
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

    @PostMapping("/angry")
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

    @PostMapping("/hate")
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

    @PostMapping("/praying")
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

    @PostMapping("/happy")
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

    @PostMapping("/sad")
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

    @PostMapping("/crying")
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

    @GetMapping
    public List<Reaction> getAllReactionFromPost(){
        logger.info("Getting all reactions from a post");
        return reactionService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReactionById(@PathVariable Integer id){
        try {
            logger.info("User want to delete reaction with id " + id);
            return reactionService.deleteReactionById(id);
        } catch (ReactionNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
