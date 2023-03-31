package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.ReactionDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.enums.ReactionType;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.ReactionRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Reaction addReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        Optional<User> myUser = userRepository.findByUsername(reaction.getUsername());
        Optional<Post> myPost = postRepository.findById(reaction.getPostId());
        if(myUser.isPresent()){
            if(myPost.isPresent()){
                Reaction reactionNew = new Reaction(myPost.get(), myUser.get(), reaction.getReactionType());
                myUser.get().getReactions().add(reactionNew);
                myPost.get().getReactions().add(reactionNew);
                return reactionRepository.save(reactionNew);
            } else {
                throw new PostNotFoundException("Post with id: '" + reaction.getPostId() + "' not found");
            }
        } else{
            throw new UserNotFoundException("User with username: '" + reaction.getUsername() + "' not found");
        }
    }

    public Reaction addLovingReaction(ReactionDTO reaction) throws UserNotFoundException, PostNotFoundException{
        Optional<User> myUser = userRepository.findByUsername(reaction.getUsername());
        Optional<Post> myPost = postRepository.findById(reaction.getPostId());
        if(myUser.isPresent()){
            if(myPost.isPresent()){
                Reaction reactionNew = new Reaction(myPost.get(), myUser.get(), ReactionType.LOVING);
                myUser.get().getReactions().add(reactionNew);
                myPost.get().getReactions().add(reactionNew);
                return reactionRepository.save(reactionNew);
            } else {
                throw new PostNotFoundException("Post with id: '" + reaction.getPostId() + "' not found");
            }
        } else{
            throw new UserNotFoundException("User with username: '" + reaction.getUsername() + "' not found");
        }
    }

    public List<Reaction> getAllReactionsFromPost (Integer postId) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            return post.get().getReactions();
        } else{
            throw new PostNotFoundException("Post with id: '" + postId + "' not found");
        }
    }

    public ResponseEntity deleteReactionById(Integer id){
        Optional<Reaction> reaction = reactionRepository.findById(id);
        if(reaction.isPresent()){
            reaction.get().getPostToReact().getReactions().remove(reaction.get());
            reaction.get().getUserWhoReacts().getReactions().remove(reaction.get());
            reactionRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Reaction deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reaction not found");

    }
}
