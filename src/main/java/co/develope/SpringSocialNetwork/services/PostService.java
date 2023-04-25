package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
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
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    FileStorageService fileStorageService;


    Logger logger = LoggerFactory.getLogger(PostService.class);


     public Post createPost(PostDTO postDTO) throws UserNotFoundException {
         logger.info(postDTO.getUsername() + " is trying to create a post");
         User myUser = userService.getUserByUsername(postDTO.getUsername());
         logger.info(postDTO.getUsername() + " has created a post");
         Post post = new Post(postDTO.getText(),myUser);
         logger.info("Saving post in the database");
         return postRepository.save(post);
     }

     public Post createPostWithImages(PostDTO postDTO, MultipartFile image) throws UserNotFoundException, IOException {
         logger.info(postDTO.getUsername() + " is trying to create a post with an image");
         User myUser = userService.getUserByUsername(postDTO.getUsername());
         logger.info("Uploading image");
         String postImage = fileStorageService.upload(image, true);
         logger.info("Image uploaded");
         Post post = new Post(postDTO.getText(), myUser, postImage);
         logger.info("Post created");
         return postRepository.save(post);
     }

    public Post getPostById(Integer postId) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            logger.info("Post with id: " + postId + " has been found");
            return optionalPost.get();
        }else{
            logger.warn("Post with id: "  + postId + " has not been found");
            throw new PostNotFoundException("Post with id: '" + postId + "' not found");
        }
    }

    public List<Post> getAllPostsFromUserId(Integer userId) throws UserNotFoundException {
        logger.info("Retrieving all posts from user with id " + userId);
        User myUser = userService.getUserById(userId);
        return myUser.getPosts();
    }

    public String getPostTextById(Integer id) throws PostNotFoundException {
        logger.info("Retrieving the text from post with id " + id);
         Post myPost = getPostById(id);
         return myPost.getText();
    }

    public byte[] getPostImageById(Integer id) throws PostNotFoundException, IOException {
        logger.info("Retrieving the image from post with id " + id);
         Post myPost = getPostById(id);
         return fileStorageService.download(myPost.getImages(), true);
    }

    public List<Post> getAllPosts(){
         logger.info("Retrieving all the posts from database");
         return postRepository.findAll();
    }

    public Post editPostById(Integer postId, String text) throws PostNotFoundException{
        Post myPost = getPostById(postId);
        logger.info("The post to edit with id " + postId + " has been found");
        myPost.setText(text);
        myPost.setUpdateDate(LocalDateTime.now());
        logger.info("The post with id " + postId + " has been edited");
        postRepository.save(myPost);
        logger.info("The edit of post " + postId + " has been saved");
        return myPost;
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
