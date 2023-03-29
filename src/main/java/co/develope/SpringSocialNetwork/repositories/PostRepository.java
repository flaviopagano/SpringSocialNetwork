package co.develope.SpringSocialNetwork.repositories;

import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {


    /** ricerca per query solo del testo dei post**/
    @Query(value = "SELECT posts.text FROM posts WHERE user_id = ?1", nativeQuery = true)
    List<String> findByUser_id(int user_id);


}
