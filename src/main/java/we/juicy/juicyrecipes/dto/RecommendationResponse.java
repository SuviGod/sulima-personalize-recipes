package we.juicy.juicyrecipes.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationResponse {
    private ResponseMetadata responseMetadata;
    private List<Item> itemList;
    private String recommendationId;

}

