package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.DTO.CommentDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public User getUserFromCommentDTO(CommentDTO comment) throws UserNotFoundException {
        Optional<User> myUser = userRepository.findByUsername(comment.getUsername());
        if(myUser.isPresent()){
            return myUser.get();
        }else{
            throw new UserNotFoundException();
        }
    }

    public Post getPostFromCommentDTO(CommentDTO comment) throws PostNotFoundException {
        Optional<Post> myPost = postRepository.findById(comment.getPostId());
        if(myPost.isPresent()){
            return myPost.get();
        }else{
            throw new PostNotFoundException();
        }
    }

}
