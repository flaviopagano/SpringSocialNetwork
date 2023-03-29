package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.CommentDTO;
import co.develope.SpringSocialNetwork.entities.Comment;
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

    public Comment getCommentFromCommentDTO(CommentDTO comment) throws UserNotFoundException, PostNotFoundException{
        Optional<User> myUser = userRepository.findByUsername(comment.getUsername());
        Optional<Post> myPost = postRepository.findById(comment.getPostId());
        if(myUser.isPresent()){
            if(myPost.isPresent()){
                return new Comment(comment.getText(), myUser.get(), myPost.get());
            }else{
                throw new PostNotFoundException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

}
