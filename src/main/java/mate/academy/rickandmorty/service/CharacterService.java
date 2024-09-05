package mate.academy.rickandmorty.service;

import mate.academy.rickandmorty.dto.CharacterDto;
import java.util.List;

public interface CharacterService {
    CharacterDto getRandomCharacter();

    List<CharacterDto> getCharactersByString(String namePart);


}
