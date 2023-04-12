package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.ReactionDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.enums.ReactionType;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.ReactionRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {

    Logger logger = LoggerFactory.getLogger(ReactionService.class);

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Reaction addReaction(ReactionDTO reaction, ReactionType reactionType) throws PostNotFoundException, UserNotFoundException {
        logger.info("Adding Reaction");
        Optional<User> myUser = userRepository.findByUsername(reaction.getUsername());
        logger.info("User with username " + reaction.getUsername() + " want to add a reaction");
        Optional<Post> myPost = postRepository.findById(reaction.getPostId());
        logger.info("To the post with id " + reaction.getPostId());
        if(myUser.isPresent()){
            if(myPost.isPresent()){
                Reaction reactionNew = new Reaction(myPost.get(), myUser.get(), reactionType);
                myUser.get().getReactions().add(reactionNew);
                myPost.get().getReactions().add(reactionNew);
                logger.info("User " + reaction.getUsername() + " added an " + reactionType + " reaction to the post with id " +
                        reaction.getPostId());
                return reactionRepository.save(reactionNew);
            } else {
                logger.warn("Post with id: '" + reaction.getPostId() + "' not found");
                throw new PostNotFoundException("Post with id: '" + reaction.getPostId() + "' not found");
            }
        } else{
            logger.warn("User with username: '" + reaction.getUsername() + "' not found");
            throw new UserNotFoundException("User with username: '" + reaction.getUsername() + "' not found");
        }
    }

    public Reaction addLovingReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        return addReaction(reaction, ReactionType.LOVING);
    }

    public Reaction addAngryReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        return addReaction(reaction, ReactionType.ANGRY);
    }

    public Reaction addHateReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        return addReaction(reaction, ReactionType.HATE);
    }

    public Reaction addPrayingReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        return addReaction(reaction, ReactionType.HAPPY);
    }

    public Reaction addHappyReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        return addReaction(reaction, ReactionType.HAPPY);
    }

    public Reaction addSadReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        return addReaction(reaction, ReactionType.SAD);
    }

    public Reaction addCryingReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        return addReaction(reaction, ReactionType.CRYING);
    }

    public List<Reaction> getAllReactionsFromPost (Integer postId) throws PostNotFoundException {
        logger.info("Trying to retrieve all reactions");
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            logger.info("Retrieving successful");
            return post.get().getReactions();
        } else{
            logger.warn("Post with id: '" + postId + "' not found");
            throw new PostNotFoundException("Post with id: '" + postId + "' not found");
        }
    }

    public ResponseEntity deleteReactionById(Integer id){
        logger.info("User wants to delete the reaction with id " + id);
        Optional<Reaction> reaction = reactionRepository.findById(id);
        if(reaction.isPresent()){
            reaction.get().getPostToReact().getReactions().remove(reaction.get());
            reaction.get().getUserWhoReacts().getReactions().remove(reaction.get());
            reactionRepository.deleteById(id);
            logger.info("Reaction deleted successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Reaction deleted successfully");
        }
        logger.info("Reaction not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reaction not found");

    }
}
