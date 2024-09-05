package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/characters")
@Tag(
        name = "Characters",
        description = "API to get info about characters"
)
public class CharacterController {
    private final CharacterService characterService;

    @Operation(
            summary = "Get a random character",
            description = "Use this method to get random character from \"Rick and Morty\""
    )
    @GetMapping("/random")
    public CharacterDto getRandomCharacter() {
        return characterService.getRandomCharacter();
    }

    @Operation(
            summary = "Get characters by string",
            description = "Use this method to find characters, whose name contains entered string"
    )
    @GetMapping("search/{name}")
    public List<CharacterDto> getCharactersByPartOfString(@PathVariable String partOfName) {
        return characterService.getCharactersByString(partOfName);
    }
}
