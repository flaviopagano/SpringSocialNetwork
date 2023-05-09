package co.develope.SpringSocialNetwork.repositories;

import co.develope.SpringSocialNetwork.entities.Reaction;
import co.develope.SpringSocialNetwork.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
    List<Reaction> findReactionByReactionType(ReactionType reactionType);
}
