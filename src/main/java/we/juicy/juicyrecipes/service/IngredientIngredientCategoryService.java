package we.juicy.juicyrecipes.service;

import we.juicy.juicyrecipes.domain.IngredientIngredientCategory;

public interface IngredientIngredientCategoryService {
    void delete(IngredientIngredientCategory ingredientIngredientCategoryToDelete);
    void deleteByCategoryIdAndIngredientId(Integer categoryId, Integer ingredientId);
}
