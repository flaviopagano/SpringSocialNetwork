package co.develope.SpringSocialNetwork.controllers;

import co.develope.SpringSocialNetwork.entities.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.*;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import co.develope.SpringSocialNetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    /**
     * Ho rifatto il metodo con l'implementazione del database usando UserRepository
     *
     * @param user Si richiede l'inserimento di un User user attraverso un RequestBody
     *             <p>
     *             poi ho salvato l'user nel database, attraverso userRepository.save()
     * @return e ho lasciato il return dell'oggetto user con il metodo toString().
     */
    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserDTO user) {
        try {
            userRepository.save(userService.getUserFromUserDTO(user));
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
        } catch (UsernameAlreadyPresentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmailAlreadyPresentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(EmailNotValidException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(PasswordNotValidException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * Ho fatto la stessa cosa con il metodo GetUser. Ho modificato il return.
     *
     * @return nel return ci sara l'oggetto userRepository che richiama il metodo findAll per prendere tutti gli
     * user dal database
     */
    @GetMapping
    public List<User> getUserList() {
        return userRepository.findAll();
    }


    /**
     *  Metodo per ottenere uno user
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

        /**
         * Metodo per aggiornare il proprio profilo, username, email, name, surname
         * per aldo: non cancellare quello che ho scritto io magari commenta il tutto
         */
        @PutMapping("/update/{id}")
        public ResponseEntity updateAllUser(@RequestBody UserDTO userDTO, @PathVariable Integer id) {
            try {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateAllUser(id, userDTO));
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }



            /*@DeleteMapping("/delete/{id}")
            public ResponseEntity deleteUser (@PathVariable Integer id) {
                Optional<User> optionalUser = userRepository.findById(id);
                if(optionalUser.isPresent()){
                    userRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body("user deleted");

                }return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


            }*/
    }
