package mate.academy.rickandmorty.dto.internal;

public record CharacterResponseDto(
        Long id,
        Long externalId,
        String name,
        String status,
        String gender
) {
}
