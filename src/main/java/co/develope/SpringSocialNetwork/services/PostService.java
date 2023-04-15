package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.IdNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import co.develope.SpringSocialNetwork.services.fileStorageServices.PostStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    PostStorageService postStorageService;


     public Post createPost(PostDTO post) throws UserNotFoundException {
         Optional<User> myUser = userRepository.findByUsername(post.getUsername());
         if(myUser.isPresent()){
             return new Post(post.getText(),myUser.get());
         }else{
             throw new UserNotFoundException("User with username: '" + post.getUsername() + "' not found");
         }
     }
     public Post createPostWithImage(PostDTO post, MultipartFile image) throws UserNotFoundException, IOException {
         Optional<User> myUser = userRepository.findByUsername(post.getUsername());
         if(myUser.isPresent()){
             String postImage = postStorageService.upload(image);

             return new Post(post.getText(),myUser.get(),postImage);
         }else{
             throw new UserNotFoundException("User with username: '" + post.getUsername() + "' not found");
         }
     }



    public List<String> getAllTextUsernameIdOnly(Integer id) throws IdNotFoundException {
        Optional<User> myUser = userRepository.findById(id);
        if(myUser.isPresent()){
            return postRepository.findAllPostsByUserId(id);
        }else{
            throw new IdNotFoundException();
        }
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
            return postRepository.findByUserWhoPosts_Id(userId);
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

    /**non funziona - come fare delete nelle many-to-many? **/
    /*public ResponseEntity deletePostById(Integer id){
        Optional<Post> myPost = postRepository.findById(id);
        if(myPost.isPresent()){
            myPost.get().getUserWhoPosts().getPosts().remove(myPost.get());
            myPost.get().getComments().remove(myPost.get());
            myPost.get().getReactions().remove(myPost.get());
            postRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Comment deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
    }
*/








}
