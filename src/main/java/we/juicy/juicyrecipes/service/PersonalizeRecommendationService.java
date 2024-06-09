package we.juicy.juicyrecipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import we.juicy.juicyrecipes.dto.Item;
import we.juicy.juicyrecipes.dto.RecommendationResponse;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonalizeRecommendationService implements RecommendationService{
    @Value("${api.recommendation.url}")
    private String apiURL;

    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public List<Integer> getRecommendedItems(Integer userId) {


        RecommendationResponse recommendationResponse
                = restTemplate.getForObject(apiURL + "?userId=" + userId, RecommendationResponse.class);

        List<Integer> recommendations = recommendationResponse.getItemList().stream()
                .map(Item::getItemId)
                .toList();
        log.info("Recommendations for user {} are {}", userId, recommendations);
        return recommendations;
    }
}
