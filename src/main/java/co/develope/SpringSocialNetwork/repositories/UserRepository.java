package co.develope.SpringSocialNetwork.repositories;

import co.develope.SpringSocialNetwork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface UserRepository extends JpaRepository <User, Integer> {

    List<User> findAll();

    User findUser(String name);

    User findById(int id);

    User findByEmail(String email);

}
