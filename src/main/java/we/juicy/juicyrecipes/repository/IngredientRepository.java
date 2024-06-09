package we.juicy.juicyrecipes.repository;

import org.springframework.data.repository.CrudRepository;
import we.juicy.juicyrecipes.domain.Ingredient;
import we.juicy.juicyrecipes.domain.IngredientCategory;

import java.util.List;
import java.util.Set;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {

    Set<Ingredient> findAllByCategories(IngredientCategory ingredientCategory);

    List<Ingredient> findAllByIdLessThan(Integer id);
}
