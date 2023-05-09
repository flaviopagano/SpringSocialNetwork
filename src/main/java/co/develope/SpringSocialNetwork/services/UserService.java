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
import java.time.LocalDate;
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

    public User createUser(UserDTO user) throws UsernameAlreadyPresentException, EmailAlreadyPresentException,
            EmailNotValidException, PasswordNotValidException {
        logger.info("Trying to create User from DTO");

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            logger.warn("Username: '" + user.getUsername() + "' already present");
            throw new UsernameAlreadyPresentException("Username: '" + user.getUsername() + "' already present");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            logger.warn("Email: '" + user.getEmail() + "' already present");
            throw new EmailAlreadyPresentException("Email: '" + user.getEmail() + "' already present");
        }

        if (!isValidPassword(user.getPassword()) || user.getPassword().contains(" ")) {
            logger.warn("Invalid Password");
            throw new PasswordNotValidException("Deve contenere tra 8 e 15 caratteri, almeno 2 lettere e 2 numeri!");
        }

        if(!patternMatches(user.getEmail(), "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-]" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            logger.warn("Invalid Email");
            throw new EmailNotValidException("Invalid email");
        }

        User userDone = new User(user.getName(), user.getSurname(), user.getUsername(), user.getEmail());
        userDone.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(userDone);
    }

    public User uploadProfilePicture(Integer userID, MultipartFile profilePicture) throws UserNotFoundException, IOException {
        User user = getUserById(userID);
        String fileName = fileStorageService.upload(profilePicture, false);
        user.setProfilePicture(fileName);
        return userRepository.save(user);
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
        logger.info("Trying to retrieve a user by username");
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            logger.info("Retrieving successful");
            return optionalUser.get();
        } else {
            logger.warn("User with username '" + username + "' not found");
            throw new UserNotFoundException("User with username: '" + username + "' not found");
        }
    }

    public byte[] getUserProfilePicture(Integer id) throws UserNotFoundException, IOException {
        User user = getUserById(id);
        String profilePicture = user.getProfilePicture();
        return fileStorageService.download(profilePicture, false);
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

    public User updateUserName(Integer id, String name) throws UserNotFoundException {
        logger.info("Trying to update a user");
        User userToUpdate = getUserById(id);
        userToUpdate.setName(name);
        userToUpdate.setUpdateDate(LocalDateTime.now());
        logger.info("Update successful");
        return userRepository.save(userToUpdate);
    }

    public User updateUserSurname(Integer id, String surname) throws UserNotFoundException {
        logger.info("Trying to update a user");
        User userToUpdate = getUserById(id);
        userToUpdate.setSurname(surname);
        userToUpdate.setUpdateDate(LocalDateTime.now());
        logger.info("Update successful");
        return userRepository.save(userToUpdate);
    }

    public User updateUserUsername(Integer id, String username) throws UserNotFoundException, UsernameAlreadyPresentException {
        logger.info("Trying to update a user");
        if(userRepository.findByUsername(username).isPresent()){
            logger.warn("Username: '" + username + "' already present");
            throw new UsernameAlreadyPresentException("Username: '" + username + "' already present");
        }
        User userToUpdate = getUserById(id);
        userToUpdate.setUsername(username);
        userToUpdate.setUpdateDate(LocalDateTime.now());
        logger.info("Update successful");
        return userRepository.save(userToUpdate);
    }

    public User updateUserDateOfBirth(Integer id, LocalDate dateTime) throws UserNotFoundException{
        logger.info("Trying to update a user");
        User userToUpdate = getUserById(id);
        userToUpdate.setDateOfBirth(dateTime);
        userToUpdate.setUpdateDate(LocalDateTime.now());
        logger.info("Update successful");
        return userRepository.save(userToUpdate);
    }

    public User updateUserPlaceOfBirth(Integer id, String placeOfBirth) throws UserNotFoundException{
        logger.info("Trying to update a user");
        User userToUpdate = getUserById(id);
        userToUpdate.setPlaceOfBirth(placeOfBirth);
        userToUpdate.setUpdateDate(LocalDateTime.now());
        logger.info("Update successful");
        return userRepository.save(userToUpdate);
    }

    public User updateUserEmail(Integer id, String email) throws UserNotFoundException, EmailNotValidException, EmailAlreadyPresentException {
        logger.info("Trying to update a user");
        if(userRepository.findByEmail(email).isPresent()){
            logger.warn("Email: '" + email + "' already present");
            throw new EmailAlreadyPresentException("Email: '" + email + "' already present");
        }
        if(!patternMatches(email, "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-]" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            logger.warn("Invalid Email");
            throw new EmailNotValidException("Invalid Email");
        }
        User userToUpdate = getUserById(id);
        userToUpdate.setEmail(email);
        userToUpdate.setUpdateDate(LocalDateTime.now());
        logger.info("Update successful");
        return userRepository.save(userToUpdate);
    }

    public User updateUserPassword(Integer id, String password) throws UserNotFoundException, PasswordNotValidException {
        logger.info("Trying to update a user");
        if (!isValidPassword(password) || password.contains(" ")) {
            logger.warn("Invalid Password");
            throw new PasswordNotValidException("Deve contenere tra 8 e 15 caratteri, almeno 2 lettere e 2 numeri!");
        }
        User userToUpdate = getUserById(id);
        userToUpdate.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        userToUpdate.setUpdateDate(LocalDateTime.now());
        logger.info("Update successful");
        return userRepository.save(userToUpdate);
    }

    public User updateProfilePicture(Integer id, MultipartFile image) throws IOException, UserNotFoundException{
        User userToUpdate = getUserById(id);
        logger.info("The user to edit with id " + id + " has been found");
        fileStorageService.deleteFile(userToUpdate.getProfilePicture(), false);
        logger.info("Uploading image");
        String newProfileImage = fileStorageService.upload(image,false);
        logger.info("Image uploaded");
        userToUpdate.setProfilePicture(newProfileImage);
        return userRepository.save(userToUpdate);
    }

    public void deleteUser (Integer id) throws UserNotFoundException {
        logger.info("Deleting user with id " + id);
        User myUser = getUserById(id);
        userRepository.delete(myUser);
    }

    /** metodi per controllo password e email **/

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





