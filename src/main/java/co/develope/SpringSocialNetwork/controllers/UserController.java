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


import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
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
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        /**
         * Metodo per aggiornare il proprio profilo, username, email, name, surname
         * Anche qui non funziona perchè invece di aggiornare lo user coi nuovi parametri, ne crea uno nuovo;
         * non so dove sia l'errore.
         * per aldo: non cancellare quello che ho scritto io magari commenta il tutto
         */
//        @PutMapping("/update-user")
//        public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) {
//            try {
//                Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());
//            if (!optionalUser.isPresent()) {
//                return ResponseEntity.notFound().build();
//            }
//                 User userToUpdate = optionalUser.get();
 //                userToUpdate.setUsername(userDTO.getUsername());
//                 userToUpdate.setEmail(userDTO.getEmail());
//
//             userRepository.save(updatedUser);
//             return ResponseEntity.ok(updatedUser);
//
//            } catch (UsernameAlreadyPresentException | EmailAlreadyPresentException e) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).build();
//            }
//        }

        @PutMapping("/update-user")
        public ResponseEntity<User> updateUser(@RequestBody User user, @RequestParam Integer id) {

            Optional<User> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            User userToUpdate = optionalUser.get();
            userRepository.save(userToUpdate);
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setEmail(user.getEmail());

            return ResponseEntity.ok(userToUpdate);

        }

        /**
         *   Metodo per cancellare lo user (non funziona un cazzo)
         *   per aldo: non cancellare quello che ho scritto io magari commenta il tutto
         */
        @DeleteMapping("/delete-user")
        public ResponseEntity <User> deleteUser (@PathVariable("id") Integer id){
            Optional<User> user = userRepository.findById(id);
            if ( !user.isPresent() ) {
                return ResponseEntity.notFound().build();
            }
            userRepository.deleteById(id);

            return ResponseEntity.ok().build();
        }
}