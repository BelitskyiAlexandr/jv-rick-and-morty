package mate.academy.rickandmorty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterDto;
import mate.academy.rickandmorty.dto.external.CharacterListDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalDataFetcher {
    @Value("${url}")
    private String url;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<CharacterDto> fetchAllCharacters() {
        List<CharacterDto> allCharacters = new ArrayList<>();
        int page = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(new URI(url + "?page=" + page))
                        .build();
                HttpResponse<String> response = httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

                String responseBody = response.body();
                CharacterListDto characterListDto = parseResponse(responseBody);
                allCharacters.addAll(characterListDto.characters());
                hasMorePages = characterListDto.pagesInfo().next() != null;
                page++;
            } catch (IOException | InterruptedException | URISyntaxException e) {
                throw new RuntimeException("Can't fetch data from API.", e);
            }
        }
        return allCharacters;
    }

    public CharacterListDto parseResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't parse response body.", e);
        }
    }
}
