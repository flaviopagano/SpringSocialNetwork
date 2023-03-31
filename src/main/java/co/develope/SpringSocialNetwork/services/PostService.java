package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.Comment;
import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.IdNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    @Autowired
    PostRepository postRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;


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
        Optional<User> myUser = userRepository.findById(id);
        if(myUser.isPresent()){
            return postRepository.findAllPostsByUserId(id);
        }else{
            throw new IdNotFoundException();
        }
    }


    /** funziona, ma non trova piu nulla quindi entra in httpstatus not found **/
    public ResponseEntity deletePostById(Integer id){
        Optional<Post> myPost = postRepository.findById(id);
        if(myPost.isPresent()){

            /** prendo la lista, assegno null alla foreign key e deleto i comment con un forEach  **/
            List<Comment> comments = myPost.get().getComments();
            for(Comment comment : comments){
                comment.setPostToComment(null);
                commentRepository.delete(comment);
            }

            postRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("The post has been deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post has not been found");
    }

    public ResponseEntity editPostById(Integer postId, PostDTO postDTO){
        Optional<Post> myPost = postRepository.findById(postId);
        if(myPost.isPresent()){
            Post post = myPost.get();
            post.setText(postDTO.getText());
            post.setUpdateDate(LocalDateTime.now());
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("The post has been edited");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post has not been found");
    }

    public List<Post> getAllPostsFromUserId(Integer userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            return optionalUser.get().getPosts();
        }else{
            throw new UserNotFoundException("User with id: '" + userId + "' not found");
        }
    }

    public Post getPostById(Integer id) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()){
            return optionalPost.get();
        }else{
            throw new PostNotFoundException("Post with id: '" + id + "' not found");
        }
    }

}
