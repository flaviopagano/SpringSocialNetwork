package co.develope.SpringSocialNetwork;

import co.develope.SpringSocialNetwork.entities.Comment;
import co.develope.SpringSocialNetwork.entities.DTO.CommentDTO;
import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.repositories.CommentRepository;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import co.develope.SpringSocialNetwork.services.CommentService;
import co.develope.SpringSocialNetwork.services.PostService;
import co.develope.SpringSocialNetwork.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
public class CommentTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    void checkCommentAdded() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Gaia");
        user.setSurname("Zanchi");
        user.setEmail("gaia@gmail.com");
        user.setPassword("prova999");
        user.setUsername("gaiaazanchi");
        user.setDateOfBirth(LocalDateTime.of(2001, 5, 16, 22, 25));
        user.setPlaceOfBirth("Bergamo");

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setPassword(user.getPlaceOfBirth());

        User userFromDB = userRepository.save(user);
        assertThat(userFromDB).isNotNull();
        assertThat(userFromDB.getId()).isNotNull();

        User userFromService = userService.getUserById(user.getId());
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getId()).isNotNull();

        User userFromFind = userRepository.findById(userFromDB.getId()).get();
        assertThat(userFromFind).isNotNull();
        assertThat(userFromFind.getId()).isNotNull();
        assertThat(userFromFind.getId()).isEqualTo(userFromDB.getId());

        Post post = new Post();
        post.setId(2);
        post.setUserWhoPosts(user);
        post.setText("Ciaooo");

        PostDTO postDTO = new PostDTO();
        postDTO.setText(post.getText());
        postDTO.setUsername(post.getUserWhoPosts().getUsername());

        Post postFromDB = postRepository.save(post);
        assertThat(postFromDB).isNotNull();
        assertThat(postFromDB.getId()).isNotNull();

        Post postFromService = postService.createPost(postDTO);
        assertThat(postFromService).isNotNull();
        assertThat(postFromService.getId()).isNotNull();

        Post postFromFind = postRepository.findById(postFromDB.getId()).get();
        assertThat(postFromFind).isNotNull();
        assertThat(postFromFind.getId()).isEqualTo(postFromDB.getId());

        Comment comment = new Comment();
        comment.setUserWhoComments(user);
        comment.setPostToComment(post);
        comment.setDescription("ciaone a te!");

        CommentDTO commentDTO = new CommentDTO(comment.getDescription(), user.getUsername(), post.getId());

        Comment commentFromDB = commentRepository.save(comment);
        assertThat(commentFromDB).isNotNull();
        assertThat(commentFromDB.getId()).isNotNull();

        Comment commentFromService = commentService.createComment(commentDTO);
        assertThat(commentFromService).isNotNull();
        assertThat(commentFromService.getId()).isNotNull();

        Comment commentFromFind = commentRepository.findById(commentFromDB.getId()).get();
        assertThat(commentFromFind).isNotNull();
        assertThat(commentFromFind.getId()).isNotNull();
        assertThat(commentFromFind.getId()).isEqualTo(commentFromDB.getId());
    }
}
