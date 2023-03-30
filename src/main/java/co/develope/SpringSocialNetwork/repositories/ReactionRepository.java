package co.develope.SpringSocialNetwork.repositories;

import co.develope.SpringSocialNetwork.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
}
