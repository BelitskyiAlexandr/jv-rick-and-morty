package mate.academy.rickandmorty.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repo.CharacterRepo;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService{
    private static final int NUMBER_OF_CHARACTERS = 826;
    private final CharacterRepo characterRepo;
    private final CharacterMapper characterMapper;
    private final Random random = new Random();

    @Override
    public CharacterDto getRandomCharacter() {
        Long id = random.nextLong(NUMBER_OF_CHARACTERS);
        Optional<Character> character = characterRepo.findById(id);
        return characterMapper.toDto(character.orElseThrow(() ->
                new EntityNotFoundException("Character not found by id:" + id)));
    }

    @Override
    public List<CharacterDto> getCharactersByString(String namePart) {
        return characterRepo.findByNameContaining(namePart).stream()
                .map(characterMapper::toDto)
                .toList();
    }
}
