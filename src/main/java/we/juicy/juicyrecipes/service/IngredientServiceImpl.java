package we.juicy.juicyrecipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.juicy.juicyrecipes.domain.Ingredient;
import we.juicy.juicyrecipes.repository.IngredientCategoryRepository;
import we.juicy.juicyrecipes.repository.IngredientRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j @RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;

    @Override
    public Set<Ingredient> findAll() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        return ingredients;
    }

    @Override
    @Transactional
    public Ingredient updateIngredient(Ingredient ingredientToUpdate) {
        ingredientToUpdate.setCategoryRelation();
        return ingredientRepository.save(ingredientToUpdate);
    }

    @Override
    public Set<Ingredient> findAllByCategory(String category) {
        return ingredientRepository.findAllByCategories(
                ingredientCategoryRepository.findByName(category));
    }

    @Override
    public Optional<Ingredient> findById(Integer id){
        return ingredientRepository.findById(id);
    }
}
