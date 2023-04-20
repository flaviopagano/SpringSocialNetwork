package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.Picture;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.*;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileStorageService fileStorageService;

    /** forse il parametro sarebbe meglio chiamarlo UserDTO userDTO) altrimenti riga 40 e' confusionaria */
    public User getUserFromUserDTO(UserDTO user) throws UsernameAlreadyPresentException, EmailAlreadyPresentException, EmailNotValidException, PasswordNotValidException {
        logger.info("Trying to retrieve User from DTO by username");
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            logger.warn("User with username " + user.getUsername() + " not found");
            throw new UsernameAlreadyPresentException("Username: '" + user.getUsername() + "' already present");
        }
        logger.info("Trying to retrieve User from DTO by email");
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            logger.warn("User with email " + user.getEmail() + " not found");
            throw new EmailAlreadyPresentException();
        }

        // Check if password contains special characters, then throw custom error
        if ((user.getPassword().contains("@") || user.getPassword().contains("#")
                || user.getPassword().contains("!") || user.getPassword().contains("~")
                || user.getPassword().contains("$") || user.getPassword().contains("%")
                || user.getPassword().contains("^") || user.getPassword().contains("&")
                || user.getPassword().contains("*") || user.getPassword().contains("(")
                || user.getPassword().contains(")") || user.getPassword().contains("-")
                || user.getPassword().contains("+") || user.getPassword().contains("/")
                || user.getPassword().contains(":") || user.getPassword().contains(".")
                || user.getPassword().contains(", ") || user.getPassword().contains("<")
                || user.getPassword().contains(">") || user.getPassword().contains("?")
                || user.getPassword().contains("|"))) {
                logger.warn("Invalid Password: special characters not allowed");
            throw new PasswordNotValidException();
        }

        //  Check if password length
        //  is between 8 and 15 characters
        if (!((user.getPassword().length() >= 8)
                && (user.getPassword().length() <= 15))) {
            logger.warn("Invalid Password: must be between 8 and 15 characters");
            throw new PasswordNotValidException();
        }

        // to check space
        if (user.getPassword().contains(" ")) {
            logger.warn("Invalid Password: space not allowed");
            throw new PasswordNotValidException();
        }


        // Check if email contains special characters, then throw custom error
        if ((user.getEmail().contains(",")
                || user.getEmail().contains("!") || user.getEmail().contains("~")
                || user.getEmail().contains("$") || user.getEmail().contains("%")
                || user.getEmail().contains("^") || user.getEmail().contains("&")
                || user.getEmail().contains("*") || user.getEmail().contains("(")
                || user.getEmail().contains(")") || user.getEmail().contains("-")
                || user.getEmail().contains("+") || user.getEmail().contains("/")
                || user.getEmail().contains(":") || user.getEmail().contains("#")
                || user.getEmail().contains(", ") || user.getEmail().contains("<")
                || user.getEmail().contains(">") || user.getEmail().contains("?")
                || user.getEmail().contains("|"))) {
                logger.warn("Invalid Email: special characters are not allowed");
            throw new EmailNotValidException();
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        return new User(user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), hashedPassword, user.getDateOfBirth(), user.getPlaceOfBirth());
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        logger.info("User is trying to retrieve a user by id");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("Retrieving successful");
            return optionalUser.get();
        } else {
            logger.warn("User with id " + id + " not found");
            throw new UserNotFoundException("User with id: '" + id + "' not found");
        }
    }

    public User updateAllUser(Integer id, UserDTO user) throws UserNotFoundException {
        logger.info("User is trying to update");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            logger.warn("User " + id + " does not exist");
            throw new UserNotFoundException("User with id: '" + id + "' not found");
        }
        User userToUpdate = optionalUser.get();
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
        Optional<User> optionalUser = userRepository.findById(userID);
        if(optionalUser.isEmpty()) throw new UserNotFoundException("User with id " + userID + " not found");
        String fileName = fileStorageService.upload(profilePicture, false);
        Picture picture = new Picture(fileName);
        optionalUser.get().setProfilePictureFilename(picture);
        return userRepository.save(optionalUser.get());
    }

    public byte[] getUserProfilePicture(Integer id) throws UserNotFoundException, IOException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) throw new UserNotFoundException("User with id " + id + " not found");
        Picture profilePicture = optionalUser.get().getProfilePicture();
        return fileStorageService.download(profilePicture.getFileName(), false);
    }

}





