package we.juicy.juicyrecipes.service;

import we.juicy.juicyrecipes.domain.IngredientCategory;

import java.util.Set;

public interface IngredientCategoryService {
    Set<IngredientCategory> findAll();
    IngredientCategory updateIngredientCategory(IngredientCategory ingredientCategoryToUpdate);
}
