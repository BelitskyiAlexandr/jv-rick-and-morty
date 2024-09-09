package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterResponseDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("characters")
@RequiredArgsConstructor
@Tag(
        name = "Characters",
        description = "API to get info about characters"
)
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/random")
    @Operation(
            summary = "Get a random character",
            description = "Use this method to get random character from \"Rick and Morty\""
    )
    public CharacterResponseDto getRandom() {
        return characterService.getRandomCharacter();
    }

    @GetMapping
    @Operation(
            summary = "Get characters by string",
            description = "Use this method to find characters, whose name contains entered string"
    )
    public List<CharacterResponseDto> search(@RequestParam String name) {
        return characterService.search(name);
    }
}
