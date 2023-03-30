package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    UserRepository userRepository;

    public Post getPostFromPostDTO(PostDTO post) throws UserNotFoundException {
        Optional<User> myUser = userRepository.findByUsername(post.getUsername());
        if(myUser.isPresent()){
            return new Post(post.getText(),myUser.get());
        }else{
            throw new UserNotFoundException("User with username: '" + post.getUsername() + "' not found");
        }
    }

}
