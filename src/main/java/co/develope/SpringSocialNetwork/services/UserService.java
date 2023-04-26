package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.*;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileStorageService fileStorageService;

    public User getUserFromUserDTO(UserDTO user) throws UsernameAlreadyPresentException, EmailAlreadyPresentException,
            EmailNotValidException, PasswordNotValidException {
        logger.info("Trying to create User from DTO");

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            logger.warn("Username: '" + user.getUsername() + "' already present");
            throw new UsernameAlreadyPresentException("Username: '" + user.getUsername() + "' already present");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            logger.warn("Email: '" + user.getEmail() + "' already present");
            throw new EmailAlreadyPresentException();
        }

        // Check if password contains special characters, then throw custom error
        if (!isValidPassword(user.getPassword()) || user.getPassword().contains(" ")) {
            logger.warn("Invalid Password");
            throw new PasswordNotValidException();
        }

        // Check if email contains special characters, then throw custom error
        if(!patternMatches(user.getEmail(), "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-]" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            logger.warn("Invalid Email");
            throw new EmailNotValidException();
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        User userDone = new User(user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), hashedPassword,
                user.getDateOfBirth(), user.getPlaceOfBirth());
        return userRepository.save(userDone);
    }


    public User getUserById(Integer id) throws UserNotFoundException {
        logger.info("Trying to retrieve a user by id");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("Retrieving successful");
            return optionalUser.get();
        } else {
            logger.warn("User with id " + id + " not found");
            throw new UserNotFoundException("User with id: '" + id + "' not found");
        }
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        logger.info("Trying to retrieve a user by id");
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            logger.info("Retrieving successful");
            return optionalUser.get();
        } else {
            logger.warn("User with username '" + username + "' not found");
            throw new UserNotFoundException("User with id: '" + username + "' not found");
        }
    }

    public List<User> getAll(){
        logger.info("Retrieving all users from database");
        return userRepository.findAll();
    }

    public User updateAllUser(Integer id, UserDTO user) throws UserNotFoundException {
        logger.info("Trying to update a user");
        User userToUpdate = getUserById(id);
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userToUpdate.setUpdateDate(LocalDateTime.now());
        logger.info("Update successful");
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


    public User uploadProfilePicture(Integer userID, MultipartFile profilePicture) throws UserNotFoundException, IOException {
        User user = getUserById(userID);
        String fileName = fileStorageService.upload(profilePicture, false);
        user.setProfilePicture(fileName);
        return userRepository.save(user);
    }

    public byte[] getUserProfilePicture(Integer id) throws UserNotFoundException, IOException {
        User user = getUserById(id);
        String profilePicture = user.getProfilePicture();
        return fileStorageService.download(profilePicture, false);
    }

    /**come fare encrypt e decrypt password al meglio**/
    /*public String getAndDecryptPassword(Integer id) throws UserNotFoundException {
        User user = getUserById(id);
        String encryptedPassword = user.getPassword();
        String password =
        return password;
    }*/

    private boolean patternMatches(String text, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(text).matches();
    }

    private boolean isValidPassword(String password) {

        if (password.length() < 8 || password.length() > 15) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }


        return (charCount >= 2 && numCount >= 2);
    }

    private boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }


    private boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

}





