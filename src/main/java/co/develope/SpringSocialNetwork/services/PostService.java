package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.IdNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    @Autowired
    PostRepository postRepository;


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


    /** non restituisce l'eccezione **/
    public List<String> getAllPostsFromId(Integer id) throws IdNotFoundException {
        Optional<User> myUser = userRepository.findById(id);   //qui provo a cercare l'user per id - non so se e' fatto bene
        if(myUser.isPresent()){
            return postRepository.findAllPostsByUserId(id);
        }else{
            throw new IdNotFoundException();
        }
    }




}
