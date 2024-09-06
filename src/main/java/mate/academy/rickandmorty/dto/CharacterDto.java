package mate.academy.rickandmorty.dto;

public record CharacterDto(Long id, String externalId, String name,
                            String status, String gender) {
}
