package co.develope.SpringSocialNetwork.repositories;

import co.develope.SpringSocialNetwork.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {
}
