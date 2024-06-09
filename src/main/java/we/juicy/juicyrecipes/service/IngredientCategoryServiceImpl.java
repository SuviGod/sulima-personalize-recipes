package we.juicy.juicyrecipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import we.juicy.juicyrecipes.domain.IngredientCategory;
import we.juicy.juicyrecipes.repository.IngredientCategoryRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j @RequiredArgsConstructor
public class IngredientCategoryServiceImpl implements IngredientCategoryService{

    private final IngredientCategoryRepository ingredientCategoryRepository;

    @Override
    public Set<IngredientCategory> findAll() {
        Set<IngredientCategory> ingredientCategories = new HashSet<>();
        ingredientCategoryRepository.findAll().forEach(ingredientCategories::add);
        return ingredientCategories;
    }

    @Override
    public IngredientCategory updateIngredientCategory(IngredientCategory ingredientCategoryToUpdate) {
        return ingredientCategoryRepository.save(ingredientCategoryToUpdate);
    }
}
