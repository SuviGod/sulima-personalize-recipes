package we.juicy.juicyrecipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import we.juicy.juicyrecipes.domain.Ingredient;

@Data
@AllArgsConstructor
public class IngredientContentsDifference {
    private Ingredient ingredient;
    private Double difference;
}
