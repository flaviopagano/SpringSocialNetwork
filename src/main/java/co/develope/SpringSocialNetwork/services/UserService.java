package co.develope.SpringSocialNetwork.services;

import co.develope.SpringSocialNetwork.DTO.UserDTO;
import co.develope.SpringSocialNetwork.entities.User;
import co.develope.SpringSocialNetwork.exceptions.EmailAlreadyPresentException;
import co.develope.SpringSocialNetwork.exceptions.UsernameAlreadyPresentException;
import co.develope.SpringSocialNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserFromUserDTO(UserDTO user) throws UsernameAlreadyPresentException, EmailAlreadyPresentException {

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new UsernameAlreadyPresentException();
        }else{
            if(userRepository.findByEmail(user.getEmail()).isPresent()){
                throw new EmailAlreadyPresentException();
            }else{
                return new User(user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getPassword());
            }
        }

    }

}



