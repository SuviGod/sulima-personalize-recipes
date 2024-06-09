package we.juicy.juicyrecipes.service;

import java.util.List;

public interface RecommendationService {
    List<Integer> getRecommendedItems(Integer userId);


}
