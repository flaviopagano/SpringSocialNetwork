package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Picture;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileStorageService fileStorageService;


    Logger logger = LoggerFactory.getLogger(PostService.class);


     public Post createPost(PostDTO postDTO) throws UserNotFoundException {
         logger.info(postDTO.getUsername() + " is trying to create a post");
         Optional<User> myUser = userRepository.findByUsername(postDTO.getUsername());
         if(myUser.isPresent()){
             logger.info(postDTO.getUsername() + " has created a post");
             return new Post(postDTO.getText(),myUser.get());
         }else{
             logger.warn(postDTO.getUsername() + " has not been found");
             throw new UserNotFoundException("User with username: '" + postDTO.getUsername() + "' not found");
         }
     }
     public Post createPostWithImages(PostDTO postDTO, MultipartFile[] image) throws UserNotFoundException, IOException {
         Optional<User> myUser = userRepository.findByUsername(postDTO.getUsername());
         if(myUser.isPresent()){
             logger.info(postDTO.getUsername() + " is trying to create a post with an image");
             logger.info("Uploading images");
             List<String> postImages = fileStorageService.uploadMany(image);
             logger.info("Images uploaded");
             if(postImages.size() > 0) {
                 List<Picture> pictures = new ArrayList<>();
                 for(int i = 0; i < postImages.size(); i++){
                     pictures.add(new Picture(postImages.get(i)));
                 }
                 Post post = new Post(postDTO.getText(), myUser.get(), pictures);
                 for(int i = 0; i < pictures.size(); i++){
                     pictures.get(i).setUserP(null);
                     pictures.get(i).setPostP(post);
                 }
                 logger.info("Post created");
                 return post;
             }else{
                 throw  new IOException("Images not found");
             }
         }else{
             logger.warn(postDTO.getUsername() + " has not been found");
             throw new UserNotFoundException("User with username: '" + postDTO.getUsername() + "' not found");
         }
     }

    public ResponseEntity editPostById(Integer postId, String text) throws PostNotFoundException{
        Optional<Post> myPost = postRepository.findById(postId);
        if(myPost.isPresent()){
            logger.info("The post to edit with id "+postId+" has been found");
            Post post = myPost.get();
            post.setText(text);
            post.setUpdateDate(LocalDateTime.now());
            logger.info("The post with id "+postId+" has been edited");
            postRepository.save(post);
            logger.info("The edit of post "+postId+" has been saved");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("The post has been edited");
        }else {
            logger.warn("The post to edit with id " + postId + " has not been found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post has not been found");
        }
    }

    public List<Post> getAllPostsFromUserId(Integer userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            logger.info("Retrieving all posts from "+optionalUser);
            return postRepository.findByUserWhoPosts_Id(userId);
        }
        logger.warn("The user with id: "+userId+" has not been found");
        throw new UserNotFoundException("User with id: '" + userId + " not found");
    }
    public Post getPostById(Integer id) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()){
            logger.info("Post with id: "+id+" has been found");
            return optionalPost.get();
        }else{
            logger.warn("Post with id: "  + id + " has not been found");
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
