package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.ReactionDTO;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.ReactionRepository;
import co.develope.SpringSocialNetwork.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reaction")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private ReactionRepository reactionRepository;

    @PostMapping("/create/loving")
    public ResponseEntity createLovingReaction (@RequestBody ReactionDTO reaction) {
        try {
            reactionService.addLovingReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/angry")
    public ResponseEntity createAngryReaction (@RequestBody ReactionDTO reaction) {
        try {
            reactionService.addAngryReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/hate")
    public ResponseEntity createHateReaction (@RequestBody ReactionDTO reaction) {
        try {
            reactionService.addHateReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/praying")
    public ResponseEntity createPrayingReaction (@RequestBody ReactionDTO reaction) {
        try {
            reactionService.addPrayingReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/happy")
    public ResponseEntity createHappyReaction (@RequestBody ReactionDTO reaction) {
        try {
            reactionService.addHappyReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/sad")
    public ResponseEntity createSadReaction (@RequestBody ReactionDTO reaction) {
        try {
            reactionService.addSadReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/create/crying")
    public ResponseEntity createCryingReaction (@RequestBody ReactionDTO reaction) {
        try {
            reactionService.addCryingReaction(reaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reaction added!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-reactions")
    public List<Reaction> getAllReactionFromPost(){
        return reactionRepository.findAll();
    }

    @DeleteMapping("/delete-reaction/{id}")
    public ResponseEntity deleteReactionById(@PathVariable Integer id){
        reactionService.deleteReactionById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Reaction deleted!");
    }
}
