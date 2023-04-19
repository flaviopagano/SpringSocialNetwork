package co.develope.SpringSocialNetwork;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.DTO.ReactionDTO;
import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.enums.ReactionType;
import co.develope.SpringSocialNetwork.repositories.PostRepository;
import co.develope.SpringSocialNetwork.repositories.ReactionRepository;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import co.develope.SpringSocialNetwork.services.PostService;
import co.develope.SpringSocialNetwork.services.ReactionService;
import co.develope.SpringSocialNetwork.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReactionServiceTest {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    void checkReactionAdded()throws Exception{
        User user = new User();
        user.setId(1);
        user.setName("Alma");
        user.setSurname("Caciula");
        user.setEmail("alma@gmail.com");
        user.setPassword("prova1234");
        user.setUsername("Erza");
        user.setDateOfBirth(LocalDateTime.of(1998, 6, 8, 13, 48));
        user.setPlaceOfBirth("Romania");

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
        post.setText("New post");

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

        Reaction reaction = new Reaction();
        reaction.setUserWhoReacts(user);
        reaction.setPostToReact(post);
        reaction.setReactionType(ReactionType.LOVING);

        ReactionDTO reactionDTO = new ReactionDTO(user.getUsername(), post.getId());

        Reaction reactionFromDB = reactionRepository.save(reaction);
        assertThat(reactionFromDB).isNotNull();
        assertThat(reactionFromDB.getId()).isNotNull();

        Reaction reactionFromService = reactionService.addLovingReaction(reactionDTO);
        assertThat(reactionFromService).isNotNull();
        assertThat(reactionFromService.getId()).isNotNull();

        Reaction reactionFromFind = reactionRepository.findById(reactionFromDB.getId()).get();
        assertThat(reactionFromFind).isNotNull();
        assertThat(reactionFromFind.getId()).isNotNull();
        assertThat(reactionFromFind.getId()).isEqualTo(reactionFromDB.getId());
    }
}
