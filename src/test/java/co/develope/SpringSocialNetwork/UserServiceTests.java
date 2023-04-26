package co.develope.SpringSocialNetwork;


import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import co.develope.SpringSocialNetwork.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void userServiceLoads() {
        assertThat(userService).isNotNull();
    }

    @Test
    void userRepositoryLoads() {
        assertThat(userRepository).isNotNull();
    }



    private User getUserById(Integer id) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/user/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        try {
            String userJSON = result.getResponse().getContentAsString();
            User user = objectMapper.readValue(userJSON, User.class);

            assertThat(user).isNotNull();
            assertThat(user.getId()).isNotNull();

            return user;
        }catch (Exception e){
            return null;
        }
    }

    private User createAUser() throws Exception {
        User user = new User();
        user.setName("AAA");
        user.setSurname("BBB");
        return createAUser(user);
    }

    private User createAUser(User user) throws Exception {
        MvcResult result = createAUserRequest();
        User userFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

        assertThat(userFromResponse).isNotNull();
        assertThat(userFromResponse.getId()).isNotNull();

        return userFromResponse;
    }

    private MvcResult createAUserRequest() throws Exception {
        User user = new User();
        user.setName("AAA");
        user.setSurname("BBB");
        return createAUserRequest();
    }

    @Test
    void createAUserTest() throws Exception {
        User userFromResponse = createAUser();
    }
}