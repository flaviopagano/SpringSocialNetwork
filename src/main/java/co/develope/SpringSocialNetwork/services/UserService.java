package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.*;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /** forse il parametro sarebbe meglio chiamarlo UserDTO userDTO) altrimenti riga 40 e' confusionaria */
    public User getUserFromUserDTO(UserDTO user) throws UsernameAlreadyPresentException, EmailAlreadyPresentException, EmailNotValidException, PasswordNotValidException {

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new UsernameAlreadyPresentException("Username: '" + user.getUsername() + "' already present");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new EmailAlreadyPresentException();
        }
        if(!user.getEmail().contains("@")){ //da aggiungere controlli
            throw new EmailNotValidException();
        }
        if(user.getPassword().contains("/")){ //da aggiungere controlli
            throw new PasswordNotValidException();
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        return new User(user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), hashedPassword);
    }


    public User getUserById(Integer id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("User with id: '" + id + "' not found");
        }
    }

    public User updateAllUser(Integer id, UserDTO user) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: '" + id + "' not found");
        }
        User userToUpdate = optionalUser.get();
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userToUpdate.setUpdateDate(LocalDateTime.now());
        return userRepository.save(userToUpdate);
    }



    /*public ResponseEntity deleteUser (Integer id)  {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("user deleted");

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }*/

}



