package we.juicy.juicyrecipes.service;

import we.juicy.juicyrecipes.domain.Ingredient;

import java.util.Optional;
import java.util.Set;

public interface IngredientService {
    Optional<Ingredient> findById(Integer id);
    Set<Ingredient> findAll();
    Ingredient updateIngredient(Ingredient ingredientToUpdate);
    Set<Ingredient> findAllByCategory(String category);
}
