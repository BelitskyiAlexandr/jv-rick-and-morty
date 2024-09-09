package mate.academy.rickandmorty.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaginationDto(@JsonProperty("next") String next) {
}
