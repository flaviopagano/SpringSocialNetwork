package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.ReactionDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.enums.ReactionType;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.ReactionNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.ReactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {

    Logger logger = LoggerFactory.getLogger(ReactionService.class);

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    private Reaction addReaction(ReactionDTO reaction, ReactionType reactionType) throws PostNotFoundException, UserNotFoundException {
        logger.info("Adding Reaction");
        User myUser = userService.getUserByUsername(reaction.getUsername());
        logger.info("User with username " + reaction.getUsername() + " want to add a reaction");
        Post myPost = postService.getPostById(reaction.getPostId());
        logger.info("To the post with id " + reaction.getPostId());
        Reaction reactionNew = new Reaction(myPost, myUser, reactionType);
        logger.info("User " + reaction.getUsername() + " added an " + reactionType + " reaction to the post with id " +
                reaction.getPostId());
        return reactionRepository.save(reactionNew);
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

    public Reaction getReactionById(Integer id) throws ReactionNotFoundException {
        logger.info("Trying to find comment with id " + id);
        Optional<Reaction> reaction = reactionRepository.findById(id);
        if(reaction.isEmpty()){
            logger.warn("Reaction with id " + id + " not found");
            throw new ReactionNotFoundException("Reaction with id: '" + id + "' not found");
        }else{
            logger.info("Retrieving successful");
            return reaction.get();
        }
    }

    //metodo inutile
    /*public List<Reaction> getAllReactionsFromPost (Integer postId) throws PostNotFoundException {
        logger.info("Trying to retrieve all reactions from post");
        Post myPost = postService.getPostById(postId);
        logger.info("Retrieving successful");
        return myPost.getReactions();
    }*/

    public List<Reaction> getAllReactions(){
        return reactionRepository.findAll();
    }

    public void deleteReactionById(Integer id) throws ReactionNotFoundException {
        logger.info("User wants to delete the reaction with id " + id);
        Reaction reaction = getReactionById(id);
        reactionRepository.delete(reaction);
        logger.info("Reaction deleted successfully");
    }

}
