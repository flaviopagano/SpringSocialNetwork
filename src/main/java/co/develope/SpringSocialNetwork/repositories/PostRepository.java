package co.develope.SpringSocialNetwork.repositories;

import co.develope.SpringSocialNetwork.entities.DTO.PostDTO;
import co.develope.SpringSocialNetwork.entities.Post;
import co.develope.SpringSocialNetwork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query(value = "SELECT users.username, posts.id, posts.text\n" +
            "FROM posts \n" +
            "JOIN users ON user_id = users.id \n" +
            "WHERE user_id = ?1", nativeQuery = true)
    List<String> findAllPostsByUserId(int user_id);

    List<Post> findByUserWhoPosts_Id(Integer userId);

}
