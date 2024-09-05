package mate.academy.rickandmorty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repo.CharacterRepo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalDataFetcher {
    private static final String HOME_URL = "https://rickandmortyapi.com/api/character";
    private final ObjectMapper objectMapper;
    private final CharacterRepo characterRepo;

    @PostConstruct
    public void init() {
        if (characterRepo.count() == 0) {
            run();
        }
    }

    public void run() {
        HttpClient httpClient = HttpClient.newHttpClient();
        String pageUrl = HOME_URL;

        try {
            while(pageUrl != "") {
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(pageUrl))
                        .build();
                HttpResponse<String> response = httpClient.send(
                        httpRequest, HttpResponse.BodyHandlers.ofString()
                );

                JsonNode node = objectMapper.readTree(response.body());
                List<Character> characters = new ArrayList<>();
                JsonNode results = node.get("results");
                for (JsonNode result : results) {
                    characters.add(parseCharacter(result));
                }
                characterRepo.saveAll(characters);
                pageUrl = node.get("info").get("next").asText();
            }
        } catch (IOException | InterruptedException e) {
            throw new EntityNotFoundException("Cannot get data from Pick and Morty API", e);
        }
    }

    private Character parseCharacter(JsonNode node) {
        Character character = new Character();
        character.setExternalId(node.get("id").asText());
        character.setName(node.get("name").asText());
        character.setGender(node.get("gender").asText());
        character.setStatus(node.get("status").asText());

        return character;
    }
}
