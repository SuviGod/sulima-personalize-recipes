package we.juicy.juicyrecipes.repository;

import org.springframework.data.repository.CrudRepository;
import we.juicy.juicyrecipes.domain.IngredientIngredientCategory;

public interface IngredientIngredientCategoryRepository extends CrudRepository<IngredientIngredientCategory, Integer> {
    void deleteByCategoryIdAndIngredientId(Integer categoryId, Integer ingredientId);
}
