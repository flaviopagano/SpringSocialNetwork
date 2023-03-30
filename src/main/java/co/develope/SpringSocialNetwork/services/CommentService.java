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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    public Comment createComment(CommentDTO comment) throws UserNotFoundException, PostNotFoundException{
        Optional<User> myUser = userRepository.findByUsername(comment.getUsername());
        Optional<Post> myPost = postRepository.findById(comment.getPostId());
        if(myUser.isPresent()){
            if(myPost.isPresent()){
                return commentRepository.save(new Comment(comment.getText(), myUser.get(), myPost.get()));
            }else{
                throw new PostNotFoundException("Post with id: '" + comment.getPostId() + "' not found");
            }
        }else{
            throw new UserNotFoundException("User with username: '" + comment.getUsername() + "' not found");
        }
    }

    public Comment getCommentById(Integer id) throws CommentNotFoundException {
        Optional<Comment> myComment = commentRepository.findById(id);
        if(myComment.isPresent()){
            return myComment.get();
        }else{
            throw new CommentNotFoundException("Comment with id: '" + id + "' not found");
        }
    }

    public List<Comment> getAllCommentsFromUser(Integer userId) throws UserNotFoundException {
        Optional<User> myUser = userRepository.findById(userId);
        if(myUser.isPresent()){
            return myUser.get().getComments();
        }else{
            throw new UserNotFoundException("User with id: '" + userId + "' not found");
        }
    }

    public List<Comment> getAllCommentsFromPost(Integer postId) throws PostNotFoundException {
        Optional<Post> myPost = postRepository.findById(postId);
        if(myPost.isPresent()){
            return myPost.get().getComments();
        }else{
            throw new PostNotFoundException("Post with id: '" + postId + "' not found");
        }
    }

    public ResponseEntity deleteCommentById(Integer id){
        Optional<Comment> myComment = commentRepository.findById(id);
        if(myComment.isPresent()){
            commentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Comment deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
    }

}
