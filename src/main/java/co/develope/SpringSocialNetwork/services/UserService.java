package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.Comment;
import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.EmailAlreadyPresentException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UsernameAlreadyPresentException;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.ReactionRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReactionRepository reactionRepository;

    public User getUserFromUserDTO(UserDTO user) throws UsernameAlreadyPresentException, EmailAlreadyPresentException {

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new UsernameAlreadyPresentException("Username: '" + user.getUsername() + "' already present");
        }else{
            if(userRepository.findByEmail(user.getEmail()).isPresent()){
                throw new EmailAlreadyPresentException();
            }else{
                return new User(user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getPassword());
            }
        }

    }

    public User getUserById(Integer id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("User with id: '" + id + "' not found");
        }
    }

    public User updateAllUser(Integer id, UserDTO user) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: '" + id + "' not found");
        }
        User userToUpdate = optionalUser.get();
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUpdateDate(LocalDateTime.now());
        return userRepository.save(userToUpdate);
    }

    /*public ResponseEntity deleteUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {

            List<Post> myListPosts = user.get().getPosts();
            for(int i = 0; i < myListPosts.size(); i++){

                Post singlePost = myListPosts.get(i);

                List<Comment> commentsOfThePost = singlePost.getComments();
                for(int j = 0; j < commentsOfThePost.size(); j++){
                    Comment singleComment = commentsOfThePost.get(j);
                    singleComment.getUserWhoComments().getComments().remove(singleComment);
                    commentsOfThePost.remove(singleComment);
                    commentRepository.delete(singleComment);
                }

                List<Reaction> reactionsOfThePost = singlePost.getReactions();
                for(int j = 0; j < reactionsOfThePost.size(); j++){
                    Reaction singleReaction = reactionsOfThePost.get(j);
                    singleReaction.getUserWhoReacts().getReactions().remove(singleReaction);
                    reactionsOfThePost.remove(singleReaction);
                    reactionRepository.delete(singleReaction);
                }
                myListPosts.remove(singlePost);
                postRepository.delete(singlePost);

            }

            List<Comment> myComments = user.get().getComments();
            for(int i = 0; i < myComments.size(); i++){
                Comment mySingleComment = myComments.get(i);
                mySingleComment.getPostToComment().getComments().remove(mySingleComment);
                myComments.remove(mySingleComment);
                commentRepository.delete(mySingleComment);
            }

            List<Reaction> myReactions = user.get().getReactions();
            for(int i = 0; i < myReactions.size(); i++){
                Reaction mySingleReaction = myReactions.get(i);
                mySingleReaction.getPostToReact().getReactions().remove(mySingleReaction);
                myReactions.remove(mySingleReaction);
                reactionRepository.delete(mySingleReaction);
            }

            userRepository.deleteById(id);

            return ResponseEntity.ok().body("User with id: '" + id + "' deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id: '" + id + "' not found");
    }*/

}



