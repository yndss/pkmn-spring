package vsa.pkmn3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import vsa.pkmn3.entities.CardEntity;
import vsa.pkmn3.repository.CardEntityRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokemonTCGServiceImpl implements PokemonTCGService {

    private static final String API_URL = "https://api.pokemontcg.io/v2/cards?q=name:{name} number:{number}";

    private final RestClient restClient;
    private final CardEntityRepository cardEntityRepository;

    public String getCardImageByName(String cardName) {
        Optional<CardEntity> cardEntity = cardEntityRepository.findByName(cardName);
        return cardEntity.map(this::getCardImageFromAPI).orElseThrow(null);
    }

    public String getCardImageFromAPI(CardEntity cardEntity) {
            String name = cardEntity.getName();

            String number = cardEntity.getNumber();

            String url = API_URL.replace("{name}", name).replace("{number}", number);

            try {
                ResponseEntity<Map> response = restClient
                        .get()
                        .uri(url)
                        .retrieve()
                        .toEntity(Map.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");

                    if (data != null) {
                        Map<String, Object> cardData = data.get(0);
                        Map<String, String> image = (Map<String, String>) cardData.get("images");
                        return image != null ? image.get("large") : null;
                    }
                }
            } catch (RestClientException e) {
                e.printStackTrace();
            }
            return null;
    }
}