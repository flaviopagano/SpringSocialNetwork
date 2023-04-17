package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.CommentDTO;
import co.develope.SpringSocialNetwork.entities.Comment;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.CommentNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.PostNotFoundException;
import co.develope.SpringSocialNetwork.exceptions.UserNotFoundException;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    public Comment createComment(CommentDTO comment) throws UserNotFoundException, PostNotFoundException{
        logger.info("Creating comment in service");
        Optional<User> myUser = userRepository.findByUsername(comment.getUsername());
        logger.info("User with username " + comment.getUsername() + " want to add a comment");
        Optional<Post> myPost = postRepository.findById(comment.getPostId());
        logger.info("To the post with id " + comment.getPostId());
        if(myUser.isPresent()){
            if(myPost.isPresent()){
                Comment comm = new Comment(comment.getText(), myUser.get(), myPost.get());
                myUser.get().getComments().add(comm);
                myPost.get().getComments().add(comm);
                logger.info("User " + comment.getUsername() + " added a comment to the post with id " +
                        comment.getPostId());
                return commentRepository.save(comm);
            }else{
                logger.warn("Post with id: '" + comment.getPostId() + "' not found");
                throw new PostNotFoundException("Post with id: '" + comment.getPostId() + "' not found");
            }
        }else{
            logger.warn("User with username: '" + comment.getUsername() + "' not found");
            throw new UserNotFoundException("User with username: '" + comment.getUsername() + "' not found");
        }
    }

    public Comment getCommentById(Integer id) throws CommentNotFoundException {
        logger.info("Trying to retrieve comment with id " + id);
        Optional<Comment> myComment = commentRepository.findById(id);
        if(myComment.isPresent()){
            logger.info("Retrieving successful");
            return myComment.get();
        }else{
            logger.warn("Comment with id " + id + " not found");
            throw new CommentNotFoundException("Comment with id: '" + id + "' not found");
        }
    }

    public List<Comment> getAllCommentsFromUser(Integer userId) throws UserNotFoundException {
        logger.info("Trying to retrieve all comments from user with id " + userId);
        Optional<User> myUser = userRepository.findById(userId);
        if(myUser.isPresent()){
            logger.info("Retrieving successful");
            return myUser.get().getComments();
        }else{
            logger.warn("User with id: '" + userId + "' not found");
            throw new UserNotFoundException("User with id: '" + userId + "' not found");
        }
    }

    public List<Comment> getAllCommentsFromPost(Integer postId) throws PostNotFoundException {
        logger.info("Trying to retrieve all comments from post with id " + postId);
        Optional<Post> myPost = postRepository.findById(postId);
        if(myPost.isPresent()){
            logger.info("Retrieving successful");
            return myPost.get().getComments();
        }else{
            logger.warn("Post with id: '" + postId + "' not found");
            throw new PostNotFoundException("Post with id: '" + postId + "' not found");
        }
    }

    public Comment updateComment(Integer id, String text) throws CommentNotFoundException {
        logger.info("User wants to update the comment with id " + id);
        Comment comment = getCommentById(id);
        comment.setDescription(text);
        logger.info("Update successful");
        return commentRepository.save(comment);
    }

    public ResponseEntity deleteCommentById(Integer id) throws CommentNotFoundException {
        logger.info("User wants to delete the comment with id " + id);
        Comment myComment = getCommentById(id);
        myComment.getPostToComment().getComments().remove(myComment);
        myComment.getUserWhoComments().getComments().remove(myComment);
        commentRepository.deleteById(id);
        logger.info("Comment deleted successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Comment deleted successfully");
    }

}
