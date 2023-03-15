package co.develope.SpringSocialNetwork.repositories;

import co.develope.SpringSocialNetwork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface UserRepository extends JpaRepository <User, String> {

    User findUser(String name);

    User findByEmail(String email);

}
