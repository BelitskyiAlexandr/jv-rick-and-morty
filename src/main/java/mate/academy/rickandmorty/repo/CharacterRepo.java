package mate.academy.rickandmorty.repo;

import java.util.List;
import mate.academy.rickandmorty.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepo extends JpaRepository<Character, Long> {
    List<Character>  findByNameContaining(String name);
}
