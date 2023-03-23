package co.develope.SpringSocialNetwork.repositories;

import co.develope.SpringSocialNetwork.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    public Optional<Post> findById(String id);

}
