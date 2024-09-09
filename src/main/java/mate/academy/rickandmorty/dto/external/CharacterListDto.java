package mate.academy.rickandmorty.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record CharacterListDto(
        @JsonProperty("results") List<CharacterDto> characters,
        @JsonProperty("info") PaginationDto pagesInfo
) {
}
