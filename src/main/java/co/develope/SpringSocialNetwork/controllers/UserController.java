package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.EmailAlreadyPresentException;
import co.develope.SpringSocialNetwork.exceptions.UsernameAlreadyPresentException;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import co.develope.SpringSocialNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    /**
     * Ho rifatto il metodo con l'implementazione del database usando UserRepository
     * @param user Si richiede l'inserimento di un User user attraverso un RequestBody
     *
     *             poi ho salvato l'user nel database, attraverso userRepository.save()
     *
     * @return e ho lasciato il return dell'oggetto user con il metodo toString().
     */
    @PostMapping ("/create")
    public ResponseEntity createUser(@RequestBody UserDTO user){
        try {
            userRepository.save(userService.getUserFromUserDTO(user));
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
        } catch (UsernameAlreadyPresentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmailAlreadyPresentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    /**
     * Ho fatto la stessa cosa con il metodo GetUser. Ho modificato il return.
     * @return nel return ci sara l'oggetto userRepository che richiama il metodo findAll per prendere tutti gli
     * user dal database
     */
    @GetMapping("/list")
    public List<User> getUserList(){
        return userRepository.findAll();
    }

}
