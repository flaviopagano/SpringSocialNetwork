package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.*;
import co.develope.SpringSocialNetwork.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDTO user) {
        try {
            logger.info("User created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
        } catch (UsernameAlreadyPresentException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmailAlreadyPresentException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(EmailNotValidException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(PasswordNotValidException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/profile-picture")
    public ResponseEntity uploadProfilePicture(@PathVariable Integer id, @RequestParam MultipartFile profilePicture) {
        try {
            logger.info("Uploading profile picture for user " + id);
            return ResponseEntity.status(HttpStatus.OK).body(userService.uploadProfilePicture(id, profilePicture));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Integer id) {
        try {
            logger.info("Getting user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-creation-date")
    public ResponseEntity getUserCreationDate(@PathVariable Integer id) {
        try {
            logger.info("Getting creation date of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getPublicationDate());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-update-date")
    public ResponseEntity getUserLastUpdateDate(@PathVariable Integer id) {
        try {
            logger.info("Getting update date of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getUpdateDate());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-name")
    public ResponseEntity getNameOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting name of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getName());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-surname")
    public ResponseEntity getSurnameOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting surname of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getSurname());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-username")
    public ResponseEntity getUsernameOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting username of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getUsername());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-birth-date")
    public ResponseEntity getBirthDateOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting birth date of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getDateOfBirth());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-place-of-birth")
    public ResponseEntity getPlaceOfBirthOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting place of birth of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getPlaceOfBirth());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-email")
    public ResponseEntity getEmailOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting email of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getEmail());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     *da fare e sistemare
     */
    /*@GetMapping("/{id}/get-password")
    public ResponseEntity getPasswordOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting password of the user with id " + id);
            return ResponseEntity.ok().body(userService.getAndDecryptPassword(id));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/

    @GetMapping("/{id}/get-posts")
    public ResponseEntity getPostsOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting posts of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getPosts());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-comments")
    public ResponseEntity getCommentsOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting comments of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getComments());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-reactions")
    public ResponseEntity getReactionsOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting reactions of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getReactions());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //non ancora implementato
    /*@GetMapping("/{id}/get-friends")
    public ResponseEntity getFriendsOfTheUser(@PathVariable Integer id) {
        try {
            logger.info("Getting friends of user with id " + id);
            return ResponseEntity.ok().body(userService.getUserById(id).getFriendList());
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/

    @RequestMapping(value = "/{id}/profile-picture", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity viewProfilePicture(@PathVariable Integer id){
        try {
            logger.info("Requested profile picture for user: " + id);
            return ResponseEntity.ok(userService.getUserProfilePicture(id));
        }catch (UserNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public List<User> getUserList() {
        logger.info("Retrieving all users from db");
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAllUser(@RequestBody UserDTO userDTO, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateAllUser(id, userDTO));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit-name")
    public ResponseEntity updateUserName(@RequestParam String name, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUserName(id, name));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit-surname")
    public ResponseEntity updateUserSurname(@RequestParam String surname, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUserSurname(id, surname));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit-username")
    public ResponseEntity updateUserUsername(@RequestParam String username, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUserUsername(id, username));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsernameAlreadyPresentException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit-date-of-birth")
    public ResponseEntity updateUserDateOfBirth(@RequestParam LocalDate date, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUserDateOfBirth(id, date));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit-place-of-birth")
    public ResponseEntity updateUserPlaceOfBirth(@RequestParam String placeOfBirth, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUserPlaceOfBirth(id, placeOfBirth));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit-email")
    public ResponseEntity updateUserEmail(@RequestParam String email, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUserEmail(id,email));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmailAlreadyPresentException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmailNotValidException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit-password")
    public ResponseEntity updateUserPassword(@RequestParam String password, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUserPassword(id, password));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PasswordNotValidException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}/edit-profile-picture")
    public ResponseEntity updateProfilePicture(@RequestPart MultipartFile file, @PathVariable Integer id) {
        try {
            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateProfilePicture(id, file));
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //mancano metodi di update dei singoli fields

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable Integer id){
        try {
            logger.info("Deleting user");
            userService.deleteUser(id);
            return ResponseEntity.status(200).body("User deleted successfully");
        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}



