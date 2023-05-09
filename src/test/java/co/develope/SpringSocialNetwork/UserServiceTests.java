package co.develope.SpringSocialNetwork;


import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import co.develope.SpringSocialNetwork.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.rmi.server.ExportException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(UserService.class);



    @Test
    void userServiceLoads() {
        assertThat(userService).isNotNull();
    }

    @Test
    void userRepositoryLoads() {
        assertThat(userRepository).isNotNull();
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

        UserDTO uDTO = new UserDTO();
        uDTO.setName("ccc");
        uDTO.setPassword("ccc");

        try {
            User userFromService = userService.createUser(uDTO);



        }catch (Exception e){
            logger.error(e.getMessage());
        }





    }
}