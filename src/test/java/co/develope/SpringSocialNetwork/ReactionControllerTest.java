package co.develope.SpringSocialNetwork;

import co.develope.SpringSocialNetwork.controllers.ReactionController;
import co.develope.SpringSocialNetwork.entities.BaseEntity;
import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.DTO.ReactionDTO;
import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.enums.ReactionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReactionControllerTest {

    @Autowired
    private ReactionController reactionController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void reactionControllerLoads(){
        assertThat(reactionController).isNotNull();
    }

//--------------------------------------------------------------------------------------------------------------

    private User createU() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Alma");
        user.setSurname("Caciula");
        user.setUsername("Erza");
        user.setEmail("alma@gmail.com");
        user.setPassword("prova1234");
        user.setDateOfBirth(LocalDateTime.of(1998, 6, 8, 13, 48));
        user.setPlaceOfBirth("Romania");

        return user;
    }

    private MvcResult createAUser() throws Exception {
        User user = new User();
        user.setName("Alma");
        user.setSurname("Caciula");
        user.setUsername("Erza");
        user.setEmail("alma@gmail.com");
        user.setPassword("prova1234");
        user.setDateOfBirth(LocalDateTime.of(1998, 6, 8, 13, 48));
        user.setPlaceOfBirth("Romania");

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPlaceOfBirth(user.getPlaceOfBirth());
        userDTO.setDateOfBirth(user.getDateOfBirth());

        String userJSON = objectMapper.writeValueAsString(userDTO);
        return this.mockMvc.perform(post("/user/create")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(userJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void createAUserTest() throws Exception {
        MvcResult userFromResponse = createAUser();
        assertThat(userFromResponse).isNotNull();
    }

    private Post creatAP() throws Exception {
        Post post = new Post();
        post.setId(2);
        post.setText("New post");
        post.setUserWhoPosts(createU());

        return post;
    }

    private MvcResult createAPost() throws Exception {
        Post post = new Post();
        post.setText("New post");
        post.setUserWhoPosts(createU());

        PostDTO postDTO = new PostDTO();
        postDTO.setText(post.getText());
        postDTO.setUsername(post.getUserWhoPosts().getUsername());

        String postJSON = objectMapper.writeValueAsString(postDTO);
        return this.mockMvc.perform(post("/post/create")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(postJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    void createAPostTest() throws Exception {
        MvcResult postFromResponse = createAPost();
        assertThat(postFromResponse).isNotNull();
    }

    private MvcResult createAReaction() throws Exception {
        Reaction reaction = new Reaction();
        reaction.setReactionType(ReactionType.LOVING);
        reaction.setUserWhoReacts(createU());
        reaction.setPostToReact(creatAP());

        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setPostId(reaction.getPostToReact().getId());
        reactionDTO.setUsername(reaction.getUserWhoReacts().getUsername());

        String postJSON = objectMapper.writeValueAsString(reactionDTO);
        return this.mockMvc.perform(post("/reaction/create/loving")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(postJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    void createAReactionTest() throws Exception {
        MvcResult reactionFromResponde = createAReaction();
        assertThat(reactionFromResponde).isNotNull();
    }

}
