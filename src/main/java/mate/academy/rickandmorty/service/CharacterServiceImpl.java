package mate.academy.rickandmorty.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.ExternalDataFetcher;
import mate.academy.rickandmorty.dto.external.CharacterDto;
import mate.academy.rickandmorty.dto.internal.CharacterResponseDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final ExternalDataFetcher dataFetcher;

    @Override
    public CharacterResponseDto getRandomCharacter() {
        long count = characterRepository.count();
        long randomId = (long) (Math.random() * count);
        Character character = characterRepository.findById(randomId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find character by id: " + randomId));
        return characterMapper.toDto(character);
    }

    @Override
    public List<CharacterResponseDto> search(String name) {
        return characterRepository.findCharacterByNameLikeIgnoreCase("%" + name + "%").stream()
                .map(characterMapper::toDto)
                .toList();
    }

    @PostConstruct
    public void init() {
        List<CharacterDto> characterDtos = dataFetcher.fetchAllCharacters();
        List<Character> characters = characterDtos.stream()
                .map(characterMapper::toModel)
                .toList();
        characterRepository.saveAll(characters);
    }
}
